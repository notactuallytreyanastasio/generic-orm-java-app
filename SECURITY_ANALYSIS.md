# SQL Security Analysis: Java Todo App

SQL injection and database security analysis of the Java todo app built on the Generic Temper ORM. This analysis focuses exclusively on SQL generation and execution — the core value proposition of the ORM.

**Analysis Date:** 2026-03-12
**Framework:** Spring Boot + SQLite JDBC + Thymeleaf
**ORM:** Generic Temper ORM (compiled to Java)

---

## How This App Uses the ORM

All user-facing CRUD operations flow through the Temper ORM's type-safe SQL generation:

| Operation | Method | SQL Source |
|-----------|--------|------------|
| SELECT lists/todos | `SrcGlobal.from(safeIdentifier).where(...).toSql()` | ORM |
| INSERT list/todo | `SrcGlobal.changeset(table, params).cast(fields).validateRequired(fields).toInsertSql()` | ORM |
| UPDATE todo title | `SrcGlobal.changeset(table, params).cast(fields).toUpdateSql(id)` | ORM |
| DELETE list/todo | `SrcGlobal.deleteSql(table, id)` | ORM |
| WHERE clauses | `SqlBuilder.appendSafe()` + `appendInt32()` | ORM |
| Toggle completed | `UPDATE todos SET completed = CASE ... WHERE id = ?` | Raw (parameterized) |
| Cascade delete | `DELETE FROM todos WHERE list_id = ?` | Raw (parameterized) |
| Count queries | `SELECT COUNT(*) FROM todos WHERE list_id = ?` | Raw (parameterized) |
| Seed count | `SELECT COUNT(*) FROM lists` | Raw (parameterized) |
| DDL | `CREATE TABLE IF NOT EXISTS ...` | Raw (static) |

### ORM SQL Generation Path

```
User input (form field)
  → Spring @RequestParam String title
    → Temper Map construction
      → SrcGlobal.changeset(tableDef, paramsMap)   [factory — sealed interface]
        → .cast(allowedFields)                      [SafeIdentifier whitelist]
          → .validateRequired(fields)               [non-null enforcement]
            → .toInsertSql()                        [type-dispatched escaping]
              → .toString()                         [rendered SQL string]
                → jdbc.execute(sql)                 [Spring JdbcTemplate]
```

For queries:
```
Route parameter (@PathVariable Long id)
  → Spring typed binding (guaranteed Long)
    → id.intValue()                                 [truncation — see JV-SQL-1]
      → SqlBuilder.appendInt32(id)                  [bare integer]
        → SrcGlobal.from(safeIdentifier).where(fragment).toSql()
          → jdbc.query(sql, rowMapper)
```

---

## SQL Injection Analysis

### ORM-Generated SQL: Protected

**SafeIdentifier validation** — Java's `SrcGlobal.safeIdentifier()` validates against `[a-zA-Z_][a-zA-Z0-9_]*`. Returns via Temper's bubble mechanism (equivalent to checked exception) on invalid input.

**SqlString escaping** — String values pass through `SqlString` which escapes `'` → `''`.

**Changeset field whitelisting** — `cast()` requires `List<SafeIdentifier>`, preventing mass assignment.

**Spring `@PathVariable Long`** — Route parameters are type-bound to `Long` by Spring's argument resolver. Non-numeric path segments return 400 before reaching the ORM.

### Raw SQL: Also Protected

All raw SQL uses Spring's `JdbcTemplate` with `?` parameterized queries:

```java
// Toggle — parameterized
jdbc.update("UPDATE todos SET completed = CASE WHEN completed = 0 THEN 1 ELSE 0 END WHERE id = ?", id);

// Cascade delete — parameterized
jdbc.update("DELETE FROM todos WHERE list_id = ?", id);

// Count — parameterized
jdbc.queryForObject("SELECT COUNT(*) FROM todos WHERE list_id = ?", Long.class, list.getId());

// Completed count — parameterized
jdbc.queryForObject("SELECT COUNT(*) FROM todos WHERE list_id = ? AND completed = 1", Long.class, list.getId());
```

**100% of raw SQL in this app uses parameterized queries.** No string concatenation of user input into SQL.

### DDL: Static

Schema creation uses hardcoded `CREATE TABLE` statements.

### PRAGMA Note

`PRAGMA foreign_keys = ON` is set per-connection. With HikariCP connection pooling, this should be set via `connection-init-sql` in `application.properties` to ensure it applies to all pooled connections.

---

## SQL Findings

| # | Severity | CWE | Finding |
|---|----------|-----|---------|
| JV-SQL-1 | LOW | CWE-681 | `Long` path params truncated to `int` via `.intValue()` for ORM calls (`appendInt32`, `deleteSql`, `toUpdateSql`). IDs above `Integer.MAX_VALUE` silently wrap. Not exploitable for injection, but could target wrong record. |
| JV-SQL-2 | LOW | CWE-16 | `PRAGMA foreign_keys = ON` set once at init, but HikariCP may create new connections that don't have the pragma. Should use `spring.datasource.hikari.connection-init-sql`. |
| JV-SQL-3 | INFO | CWE-89 | ORM SQL executed via `jdbc.execute(sql)` as pre-rendered string. Escaping is correct, but parameterized would be strictly safer. |
| JV-SQL-4 | INFO | CWE-400 | SELECT queries use `toSql()` without limits. `safeToSql(defaultLimit)` available but unused. |

### ORM-Level Concerns (shared across all apps)

| # | Severity | CWE | Finding |
|---|----------|-----|---------|
| ORM-1 | MEDIUM | CWE-89 | `toInsertSql`/`toUpdateSql` pass `pair.key` (a `String`) to `appendSafe`. Safe by construction but not type-enforced. |
| ORM-2 | LOW | CWE-89 | `SqlDate.formatTo` wraps `value.toString()` in quotes without escaping. |
| ORM-3 | LOW | CWE-20 | `SqlFloat64.formatTo` can produce `NaN`/`Infinity`. |

---

## Evolution: Temper-Level Remediation

**Date:** 2026-03-12
**Commit:** [`1df8c7a`](https://github.com/notactuallytreyanastasio/alloy/commit/1df8c7a)

The security analysis above identified 3 ORM-level concerns (ORM-1, ORM-2, ORM-3) shared across all 6 app implementations. Because the ORM is written once in Temper and compiled to all backends, fixing these issues at the Temper source level automatically resolves them in every language — including this Java app.

### What Changed

**ORM-1 (MEDIUM → RESOLVED): Column name type safety in INSERT/UPDATE SQL**

The `toInsertSql()` and `toUpdateSql()` methods previously passed `pair.getKey()` (a raw `String`) to `appendSafe()`. While safe by construction (keys originated from `cast()` via `SafeIdentifier.getSqlValue()`), the type system didn't enforce this. A future refactor could have silently introduced an unvalidated code path.

The fix routes column names through the looked-up `FieldDef.getName().getSqlValue()` — a `SafeIdentifier` — so the column name in the generated SQL always comes from a validated identifier, not a raw map key.

**ORM-2 (LOW → RESOLVED): SqlDate quote escaping**

`SqlDate.formatTo()` previously wrapped `value.toString()` in quotes without escaping. The fix adds character-by-character quote escaping identical to `SqlString.formatTo()`, ensuring defense-in-depth against any future Date format that might contain single quotes.

**ORM-3 (LOW → RESOLVED): SqlFloat64 NaN/Infinity handling**

`SqlFloat64.formatTo()` previously called `value.toString()` directly, which could produce `NaN`, `Infinity`, or `-Infinity` — none valid SQL literals. The fix checks for these values and renders `NULL` instead, which is the safest SQL representation for non-representable floating-point values.

### Why This Matters

This is the core value proposition of a cross-compiled ORM: **one fix in Temper source propagates to all 6 backends simultaneously.** The same commit that fixed the Java compiled output also fixed JavaScript, Python, Rust, Lua, and C#. No per-language patches needed. No risk of inconsistent fixes across implementations.

### Updated Status

| Finding | Original | Current | Resolution |
|---------|----------|---------|------------|
| ORM-1 | MEDIUM | RESOLVED | Column names routed through `SafeIdentifier` |
| ORM-2 | LOW | RESOLVED | `SqlDate.formatTo()` now escapes quotes |
| ORM-3 | LOW | RESOLVED | `SqlFloat64.formatTo()` renders NaN/Infinity as `NULL` |
| ORM-4 | INFO | ACKNOWLEDGED | Design limitation — escaping-based, not parameterized |

---

## JOIN Feature Security Analysis

**Analysis Date:** 2026-03-13
**ORM Source:** `src/query.temper.md` (Temper source, compiled to Java in `vendor/orm/`)
**Feature:** INNER JOIN, LEFT JOIN, RIGHT JOIN, FULL OUTER JOIN support with `SafeIdentifier` table names and `SqlFragment` ON conditions

### Architecture Review

The JOIN feature adds the following types to the ORM:

```
JoinType (sealed interface)
  ├── InnerJoin   → keyword() = "INNER JOIN"
  ├── LeftJoin    → keyword() = "LEFT JOIN"
  ├── RightJoin   → keyword() = "RIGHT JOIN"
  └── FullJoin    → keyword() = "FULL OUTER JOIN"

JoinClause(joinType: JoinType, table: SafeIdentifier, onCondition: SqlFragment)
```

The `Query` class gains a `joinClauses: List<JoinClause>` field and convenience methods:

```
Query.join(joinType: JoinType, table: SafeIdentifier, onCondition: SqlFragment): Query
Query.innerJoin(table: SafeIdentifier, onCondition: SqlFragment): Query
Query.leftJoin(table: SafeIdentifier, onCondition: SqlFragment): Query
Query.rightJoin(table: SafeIdentifier, onCondition: SqlFragment): Query
Query.fullJoin(table: SafeIdentifier, onCondition: SqlFragment): Query
```

A `col(table: SafeIdentifier, column: SafeIdentifier): SqlFragment` helper produces qualified column references (`table.column`).

### Security Properties

**JOIN table names: SAFE.** The `table` parameter requires `SafeIdentifier`, which is validated against `[a-zA-Z_][a-zA-Z0-9_]*`. An attacker cannot inject SQL via a JOIN table name — `safeIdentifier("users; DROP TABLE users")` would bubble (fail) at construction time. The test suite verifies this explicitly.

**JOIN type keywords: SAFE.** `JoinType` is a sealed interface with exactly 4 implementations. Each returns a hardcoded string literal from `keyword()`. No user input can influence the JOIN type keyword. The `appendSafe()` call for the keyword is correct because these strings are compile-time constants.

**ON conditions: SAFE (with caveats).** The `onCondition` parameter is `SqlFragment`, not `String`. A `SqlFragment` can only be constructed via:
- The `sql` tagged template (in Temper source), which separates SQL structure from values
- `SqlBuilder` methods (`appendSafe`, `appendString`, `appendInt32`, etc.), which type-dispatch to the appropriate `SqlPart`
- The `col()` helper, which takes two `SafeIdentifier` values

There is no path for a raw user string to become a `SqlFragment` without going through escaping or validation. However, in the Java compiled output, `SqlBuilder.appendSafe(String)` accepts any `String` — it is the caller's responsibility to only pass safe values. The Temper source enforces this structurally, but Java consumers of the compiled ORM could misuse `appendSafe()` directly.

**SQL rendering order: CORRECT.** JOINs are rendered after FROM and before WHERE, which is standard SQL clause ordering:
```sql
SELECT ... FROM table JOIN other ON condition WHERE ... ORDER BY ... LIMIT ... OFFSET ...
```

**Immutability: PRESERVED.** The `join()` method follows the same immutable-copy pattern as `where()`, `orderBy()`, etc. A new `Query` is returned with the JOIN appended; the original is unchanged.

### Potential Risks for App Consumers

| # | Severity | Risk | Mitigation |
|---|----------|------|------------|
| JOIN-1 | LOW | Java consumers could call `SqlBuilder.appendSafe()` with user input in ON conditions | The Temper `sql` tag prevents this at source level; Java callers should always use `SqlBuilder.appendString()` for user values |
| JOIN-2 | INFO | JOINs multiply result set size — a 3-way JOIN with no WHERE/LIMIT could produce very large results | Use `safeToSql(defaultLimit)` to cap results. The ORM makes this easy but does not enforce it |
| JOIN-3 | INFO | `col()` helper output goes through `appendSafe` since both parts are `SafeIdentifier` — this is correct but relies on `SafeIdentifier` validation being sound | `SafeIdentifier` regex is `[a-zA-Z_][a-zA-Z0-9_]*` — no metacharacters possible. This is sound. |
| JOIN-4 | INFO | RIGHT JOIN and FULL OUTER JOIN are not supported by SQLite (the database used by this Java app) | SQLite silently ignores or errors on these — no security impact, but a correctness consideration for app developers |

### Test Coverage

The Temper test suite (`src/query_test.temper.md`) includes dedicated JOIN tests:

| Test | What it verifies |
|------|-----------------|
| `innerJoin produces INNER JOIN` | Basic INNER JOIN SQL generation |
| `leftJoin produces LEFT JOIN` | LEFT JOIN keyword |
| `rightJoin produces RIGHT JOIN` | RIGHT JOIN keyword |
| `fullJoin produces FULL OUTER JOIN` | FULL OUTER JOIN keyword |
| `chained joins` | Multiple JOINs in sequence |
| `join with where and orderBy` | JOINs composed with WHERE, ORDER BY, LIMIT |
| `col helper produces qualified reference` | `col(table, column)` → `table.column` |
| `join with col helper` | End-to-end: `col()` in ON condition with JOIN |
| `safeIdentifier rejects metacharacters` | Injection attempt on table name → bubble |
| `where with injection attempt` | SQL injection in string value → properly escaped |

### Impact on This Java App

**The Java todo app does not currently use JOINs.** The `TodoRepository` queries `lists` and `todos` tables independently, using the `list_id` foreign key with WHERE conditions rather than JOINs. The `populateListCounts()` method uses separate COUNT queries per list.

However, the JOIN feature is available in the vendored ORM and could be adopted. If the app were to use JOINs (e.g., to replace the N+1 count queries with a single `LEFT JOIN` + `GROUP BY`), the security model would be maintained because:
1. Table names must pass `SafeIdentifier` validation
2. ON conditions must be `SqlFragment` (type-safe SQL fragments)
3. The immutable query builder prevents accidental mutation

### Verdict

**The JOIN feature maintains the ORM's security invariants.** All attack surfaces — table names, keywords, ON conditions — are protected by the same type-safe mechanisms used for existing SELECT, WHERE, and ORDER BY functionality. The sealed `JoinType` interface ensures JOIN keywords cannot be tampered with. The `SafeIdentifier` requirement for table names prevents identifier injection. The `SqlFragment` requirement for ON conditions prevents condition injection.

---

## SQL-Relevant CWE Mapping

This table shows only the CWEs from the MITRE Top 25 (2024) that are directly relevant to SQL security.

| Rank | CWE ID | CWE Name | Status | Evidence / Notes |
|------|--------|----------|--------|------------------|
| 3 | CWE-89 | SQL Injection | **MITIGATED** | All ORM-generated SQL uses `SafeIdentifier` (regex-validated `[a-zA-Z_][a-zA-Z0-9_]*`) for table/column names, `SqlString` with `'`→`''` escaping for string values, and `SqlInt32`/`SqlInt64` for numeric values. All raw SQL (toggle, cascade delete, counts) uses JdbcTemplate `?` parameterized queries. DDL is static. Residual note: ORM SQL is rendered as a complete string and executed via `jdbc.execute()` rather than parameterized — escaping is correct but parameterized would be defense-in-depth (JV-SQL-3). |
| 6 | CWE-20 | Improper Input Validation | **PARTIALLY MITIGATED (SQL context)** | Spring `@PathVariable Long` provides type-level validation for IDs (non-numeric returns 400). `SqlFloat64` now handles NaN/Infinity (ORM-3 resolved). String inputs have no length limits — the ORM's `validateLength()` is available but unused. SQL inputs pass through type-safe escaping but lack upper-bound validation. |
| 13 | CWE-190 | Integer Overflow or Wraparound | **LOW RISK (SQL context)** | `Long` path parameters are truncated to `int` via `.intValue()` for ORM calls. IDs above `Integer.MAX_VALUE` (2,147,483,647) silently wrap to negative values. Not exploitable for injection (integers are rendered bare), but could target unintended records. See JV-SQL-1. SQLite's `INTEGER PRIMARY KEY AUTOINCREMENT` generates 64-bit rowids, so this is theoretically reachable with very large datasets. |
| 22 | CWE-400 | Uncontrolled Resource Consumption | **PARTIALLY MITIGATED (SQL context)** | ORM provides `safeToSql(defaultLimit)` but the app uses `toSql()` without limits for `findAllLists()` and `findItemsByListId()`. An attacker could create unbounded lists/todos. Additionally, `findAllLists()` performs N+1 queries — one count query per list — which scales linearly. The `findListById()` and `findItemById()` methods do use `.limit(1)` correctly. JOINs (if adopted) multiply result set sizes without enforced limits. |
| 24 | CWE-915 | Improperly Controlled Modification of Dynamically-Determined Object Attributes (Mass Assignment) | **MITIGATED** | The ORM's `cast(List<SafeIdentifier>)` method acts as an explicit field whitelist. Only `SafeIdentifier` values pre-declared in the repository (`nameField`, `titleField`, `completedField`, etc.) are accepted. Extra form parameters are ignored — they are not present in the `params` map key set matched against the `cast` whitelist. Spring's `@RequestParam` binding is also explicit (named parameters, not object binding). No `@ModelAttribute` with automatic property binding. |

---

## Verdict

**No SQL injection vulnerabilities found.** The ORM handles all user-input-to-SQL paths with type-safe identifier validation and string escaping. This app achieves 100% parameterized raw SQL via Spring's JdbcTemplate — the strongest raw-SQL safety posture of all 6 app implementations alongside JavaScript.

### SQL-Specific Summary

The Java todo app demonstrates robust SQL security through:
1. ORM-generated SQL uses validated `SafeIdentifier` for all table/column names
2. User string inputs are escaped via `SqlString` (`'` → `''`)
3. All raw SQL uses parameterized queries (`?` placeholders)
4. Mass assignment is prevented via explicit field whitelisting with `cast()`
5. JOIN feature (if adopted) maintains the same security invariants

Minor residual concerns:
- Integer truncation from `Long` to `int` (JV-SQL-1) — low severity
- Missing connection-init-sql for PRAGMA foreign_keys (JV-SQL-2) — configuration issue
- ORM SQL rendered as strings rather than parameterized (JV-SQL-3) — design choice
- Unbounded queries possible without explicit limits (JV-SQL-4) — `safeToSql()` available but unused

---

## Consolidated Findings Summary

**Last Updated:** 2026-03-13

### App-Level SQL Findings

| # | Severity | CWE | Finding | Status |
|---|----------|-----|---------|--------|
| JV-SQL-1 | LOW | CWE-681/190 | `Long` → `int` truncation via `.intValue()` for ORM calls | OPEN |
| JV-SQL-2 | LOW | CWE-16 | `PRAGMA foreign_keys` not set via connection-init-sql | OPEN |
| JV-SQL-3 | INFO | CWE-89 | ORM SQL executed as rendered string, not parameterized | OPEN |
| JV-SQL-4 | INFO | CWE-400 | `toSql()` used without limits; `safeToSql()` available | OPEN |

### ORM-Level Findings

| # | Severity | CWE | Finding | Status |
|---|----------|-----|---------|--------|
| ORM-1 | MEDIUM | CWE-89 | Column names now routed through `SafeIdentifier` | **RESOLVED** |
| ORM-2 | LOW | CWE-89 | `SqlDate.formatTo()` now escapes quotes | **RESOLVED** |
| ORM-3 | LOW | CWE-20 | `SqlFloat64.formatTo()` renders NaN/Infinity as `NULL` | **RESOLVED** |
| ORM-4 | INFO | — | Escaping-based design (not parameterized) | ACKNOWLEDGED |

### JOIN Feature Findings

| # | Severity | CWE | Finding | Status |
|---|----------|-----|---------|--------|
| JOIN-1 | LOW | CWE-89 | Java callers could misuse `appendSafe()` in ON conditions | ACKNOWLEDGED |
| JOIN-2 | INFO | CWE-400 | JOINs multiply result sets; `safeToSql()` recommended | ACKNOWLEDGED |
| JOIN-3 | INFO | — | `col()` helper correctness depends on `SafeIdentifier` soundness | VERIFIED |
| JOIN-4 | INFO | — | RIGHT/FULL OUTER JOIN unsupported by SQLite | ACKNOWLEDGED |

### Totals by Severity (SQL-Only Findings)

| Severity | Open | Resolved/Verified | Total |
|----------|------|-------------------|-------|
| MEDIUM | 0 | 1 | 1 |
| LOW | 3 | 2 | 5 |
| INFO | 6 | 0 | 6 |
| **Total** | **9** | **3** | **12** |
