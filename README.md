# Generic ORM — Java Demo App

A todo-list application built with Spring Boot, Thymeleaf, and SQLite JDBC, demonstrating the [Generic ORM](https://github.com/notactuallytreyanastasio/generic_orm) compiled from Temper to Java.

## Stack

| Component | Technology |
|-----------|-----------|
| Framework | Spring Boot 3.2.5 |
| Templates | Thymeleaf |
| Database | SQLite via sqlite-jdbc 3.45 |
| ORM | [Generic ORM](https://github.com/notactuallytreyanastasio/generic_orm) (vendored) |
| Port | 5004 |

## ORM Usage

```java
import orm.src.SrcGlobal;

// SELECT — type-safe query builder
String q = SrcGlobal.from(SrcGlobal.safeIdentifier("todos"))
    .where(whereFragment)
    .orderBy(SrcGlobal.safeIdentifier("created_at"), true)
    .toSql().toString();

// INSERT — changeset pipeline with field whitelisting
var cs = SrcGlobal.changeset(tableDef, params)
    .cast(List.of(SrcGlobal.safeIdentifier("title")))
    .validateRequired(List.of(SrcGlobal.safeIdentifier("title")));
String sql = cs.toInsertSql().toString();

// DELETE — validated identifier
String sql = SrcGlobal.deleteSql(tableDef, id).toString();
```

## Security Model

- **Zero SQL injection vulnerabilities** — all queries generated through the ORM
- `SrcGlobal.safeIdentifier()` validates table/column names against `[a-zA-Z_][a-zA-Z0-9_]*`
- Sealed `SqlPart` hierarchy handles per-type value escaping
- `Changeset.cast()` enforces field whitelisting (CWE-915 prevention)
- JdbcTemplate executes ORM-generated SQL strings
- DDL (`CREATE TABLE`) is the only raw SQL — static strings with no user input

## Running

```bash
mvn spring-boot:run
# Open http://localhost:5004
```

## Vendored ORM

The `vendor/` directory contains the ORM compiled from Temper to Java source files. Maven includes them via `build-helper-maven-plugin`. Updated automatically by CI when the ORM source changes.

---

Part of the [Generic ORM](https://github.com/notactuallytreyanastasio/generic_orm) project — write once in Temper, secure everywhere.
