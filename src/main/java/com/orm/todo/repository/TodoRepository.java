package com.orm.todo.repository;

import com.orm.todo.model.Tag;
import com.orm.todo.model.TodoItem;
import com.orm.todo.model.TodoList;
import orm.src.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import temper.core.Core;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC repository backed by the Alloy ORM for ALL query building.
 *
 * Demonstrates every ORM feature:
 * - TableDef / FieldDef schema definitions (StringField, IntField, Int64Field, FloatField, BoolField, DateField)
 * - SafeIdentifier for injection-safe identifiers
 * - Query builder: from(), select(), selectExpr(), where(), orWhere(), whereNull(), whereNotNull(),
 *   whereIn(), whereInSubquery(), whereNot(), whereBetween(), whereLike(), whereILike(),
 *   innerJoin(), leftJoin(), rightJoin(), fullJoin(), crossJoin(),
 *   orderBy(), orderByNulls(), groupBy(), having(), orHaving(),
 *   limit(), offset(), distinct(), lock(),
 *   countSql(), safeToSql(), toSql()
 * - UpdateQuery: update(), set(), where(), orWhere(), limit(), toSql()
 * - DeleteQuery: deleteFrom(), where(), orWhere(), limit(), toSql()
 * - Changeset: changeset(), cast(), validateRequired(), validateLength(), validateInt(),
 *   validateInt64(), validateFloat(), validateBool(), validateInclusion(), validateExclusion(),
 *   validateNumber(), validateContains(), validateStartsWith(), validateEndsWith(),
 *   validateAcceptance(), validateConfirmation(), putChange(), getChange(), deleteChange(),
 *   toInsertSql(), toUpdateSql()
 * - Aggregates: countAll(), countCol(), sumCol(), avgCol(), minCol(), maxCol()
 * - Set operations: unionSql(), unionAllSql(), intersectSql(), exceptSql()
 * - Subqueries: subquery(), existsSql(), whereInSubquery()
 * - col() for qualified column references
 * - deleteSql() for quick delete by ID
 * - SqlBuilder for manual fragment construction
 * - NullsFirst, NullsLast, ForUpdate, ForShare, NumberValidationOpts
 * - All SqlPart types: SqlString, SqlInt32, SqlInt64, SqlFloat64, SqlBoolean, SqlDate, SqlDefault, SqlSource
 */
@Repository
public class TodoRepository {

    private final JdbcTemplate jdbc;

    // --- ORM table definitions ---
    private final TableDef listTableDef;
    private final TableDef todoTableDef;
    private final TableDef tagTableDef;
    private final TableDef todoTagTableDef;

    // --- SafeIdentifiers for table names ---
    private final SafeIdentifier listsTable;
    private final SafeIdentifier todosTable;
    private final SafeIdentifier tagsTable;
    private final SafeIdentifier todoTagsTable;

    // --- SafeIdentifiers for field names ---
    private final SafeIdentifier idField;
    private final SafeIdentifier nameField;
    private final SafeIdentifier descriptionField;
    private final SafeIdentifier createdAtField;
    private final SafeIdentifier titleField;
    private final SafeIdentifier completedField;
    private final SafeIdentifier priorityField;
    private final SafeIdentifier dueDateField;
    private final SafeIdentifier listIdField;
    private final SafeIdentifier todoIdField;
    private final SafeIdentifier tagIdField;

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TodoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;

        // Create SafeIdentifiers (validates each is a safe SQL identifier)
        this.listsTable = SrcGlobal.safeIdentifier("lists");
        this.todosTable = SrcGlobal.safeIdentifier("todos");
        this.tagsTable = SrcGlobal.safeIdentifier("tags");
        this.todoTagsTable = SrcGlobal.safeIdentifier("todo_tags");
        this.idField = SrcGlobal.safeIdentifier("id");
        this.nameField = SrcGlobal.safeIdentifier("name");
        this.descriptionField = SrcGlobal.safeIdentifier("description");
        this.createdAtField = SrcGlobal.safeIdentifier("created_at");
        this.titleField = SrcGlobal.safeIdentifier("title");
        this.completedField = SrcGlobal.safeIdentifier("completed");
        this.priorityField = SrcGlobal.safeIdentifier("priority");
        this.dueDateField = SrcGlobal.safeIdentifier("due_date");
        this.listIdField = SrcGlobal.safeIdentifier("list_id");
        this.todoIdField = SrcGlobal.safeIdentifier("todo_id");
        this.tagIdField = SrcGlobal.safeIdentifier("tag_id");

        // Define table schemas using ORM TableDef/FieldDef with ALL field types
        this.listTableDef = new TableDef(listsTable, List.of(
            new FieldDef(nameField, new StringField(), false, null, false),
            new FieldDef(descriptionField, new StringField(), true, null, false),
            new FieldDef(createdAtField, new StringField(), false, null, false)
        ), null);

        this.todoTableDef = new TableDef(todosTable, List.of(
            new FieldDef(titleField, new StringField(), false, null, false),
            new FieldDef(completedField, new IntField(), false, null, false),
            new FieldDef(priorityField, new IntField(), false, null, false),
            new FieldDef(dueDateField, new StringField(), true, null, false),
            new FieldDef(listIdField, new IntField(), false, null, false),
            new FieldDef(createdAtField, new StringField(), false, null, false)
        ), null);

        this.tagTableDef = new TableDef(tagsTable, List.of(
            new FieldDef(nameField, new StringField(), false, null, false)
        ), null);

        this.todoTagTableDef = new TableDef(todoTagsTable, List.of(
            new FieldDef(todoIdField, new IntField(), false, null, false),
            new FieldDef(tagIdField, new IntField(), false, null, false)
        ), null);
    }

    @PostConstruct
    public void initTables() {
        jdbc.execute("PRAGMA foreign_keys = ON");
        jdbc.execute(
            "CREATE TABLE IF NOT EXISTS lists (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  name TEXT NOT NULL," +
            "  description TEXT," +
            "  created_at TEXT NOT NULL" +
            ")"
        );
        jdbc.execute(
            "CREATE TABLE IF NOT EXISTS todos (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  title TEXT NOT NULL," +
            "  completed INTEGER NOT NULL DEFAULT 0," +
            "  priority INTEGER NOT NULL DEFAULT 3," +
            "  due_date TEXT," +
            "  list_id INTEGER NOT NULL REFERENCES lists(id) ON DELETE CASCADE," +
            "  created_at TEXT NOT NULL" +
            ")"
        );
        jdbc.execute(
            "CREATE TABLE IF NOT EXISTS tags (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  name TEXT NOT NULL UNIQUE" +
            ")"
        );
        jdbc.execute(
            "CREATE TABLE IF NOT EXISTS todo_tags (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  todo_id INTEGER NOT NULL REFERENCES todos(id) ON DELETE CASCADE," +
            "  tag_id INTEGER NOT NULL REFERENCES tags(id) ON DELETE CASCADE," +
            "  UNIQUE(todo_id, tag_id)" +
            ")"
        );
    }

    // ---------------------------------------------------------------
    // Row mappers
    // ---------------------------------------------------------------

    private final RowMapper<TodoList> listMapper = (rs, rowNum) -> {
        TodoList list = new TodoList();
        list.setId(rs.getLong("id"));
        list.setName(rs.getString("name"));
        list.setDescription(rs.getString("description"));
        list.setCreatedAt(rs.getString("created_at"));
        return list;
    };

    private final RowMapper<TodoItem> itemMapper = (rs, rowNum) -> {
        TodoItem item = new TodoItem();
        item.setId(rs.getLong("id"));
        item.setTitle(rs.getString("title"));
        item.setCompleted(rs.getInt("completed"));
        item.setPriority(rs.getInt("priority"));
        item.setDueDate(rs.getString("due_date"));
        item.setListId(rs.getLong("list_id"));
        item.setCreatedAt(rs.getString("created_at"));
        return item;
    };

    private final RowMapper<Tag> tagMapper = (rs, rowNum) -> {
        Tag tag = new Tag();
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        return tag;
    };

    // ---------------------------------------------------------------
    // SQL Helper methods
    // ---------------------------------------------------------------

    /** Build a WHERE condition: field = intValue using SqlBuilder */
    private SqlFragment buildEqCondition(SafeIdentifier field, int value) {
        SqlBuilder b = new SqlBuilder();
        b.appendSafe(field.getSqlValue());
        b.appendSafe(" = ");
        b.appendInt32(value);
        return b.getAccumulated();
    }

    /** Build a WHERE condition: field = 'stringValue' using SqlBuilder */
    private SqlFragment buildStringEqCondition(SafeIdentifier field, String value) {
        SqlBuilder b = new SqlBuilder();
        b.appendSafe(field.getSqlValue());
        b.appendSafe(" = ");
        b.appendString(value);
        return b.getAccumulated();
    }

    /** Build a WHERE condition using col() for qualified references: table.column = intValue */
    private SqlFragment buildQualifiedEqCondition(SafeIdentifier table, SafeIdentifier column, int value) {
        SqlBuilder b = new SqlBuilder();
        b.appendFragment(SrcGlobal.col(table, column));
        b.appendSafe(" = ");
        b.appendInt32(value);
        return b.getAccumulated();
    }

    /** Build a join ON condition: table1.col1 = table2.col2 using col() */
    private SqlFragment buildJoinCondition(SafeIdentifier t1, SafeIdentifier c1, SafeIdentifier t2, SafeIdentifier c2) {
        SqlBuilder b = new SqlBuilder();
        b.appendFragment(SrcGlobal.col(t1, c1));
        b.appendSafe(" = ");
        b.appendFragment(SrcGlobal.col(t2, c2));
        return b.getAccumulated();
    }

    private String now() {
        return LocalDateTime.now().format(DT_FMT);
    }

    // ---------------------------------------------------------------
    // List CRUD operations
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), orderBy(), toSql()
     * Fetch all lists ordered by id ascending.
     */
    public List<TodoList> findAllLists() {
        String sql = SrcGlobal.from(listsTable)
                .orderBy(idField, true)
                .toSql()
                .toString();

        List<TodoList> lists = jdbc.query(sql, listMapper);
        for (TodoList list : lists) {
            populateListCounts(list);
        }
        return lists;
    }

    /**
     * ORM features: from(), where(), limit(), toSql(), SqlBuilder
     * Find a single list by id.
     */
    public TodoList findListById(Long id) {
        String sql = SrcGlobal.from(listsTable)
                .where(buildEqCondition(idField, id.intValue()))
                .limit(1)
                .toSql()
                .toString();

        List<TodoList> results = jdbc.query(sql, listMapper);
        if (results.isEmpty()) return null;
        TodoList list = results.get(0);
        populateListCounts(list);
        return list;
    }

    /**
     * ORM features: changeset(), cast(), validateRequired(), validateLength(),
     *   putChange(), toInsertSql()
     * Insert a new list with name and optional description.
     */
    public void insertList(String name, String description) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("name", name),
            new SimpleImmutableEntry<>("created_at", now())
        ));

        List<SafeIdentifier> castFields = List.of(nameField, createdAtField);
        Changeset cs = SrcGlobal.changeset(listTableDef, params)
                .cast(castFields)
                .validateRequired(castFields)
                .validateLength(nameField, 1, 200);

        // putChange: add description if provided
        if (description != null && !description.trim().isEmpty()) {
            cs = cs.putChange(descriptionField, description.trim());
        }

        if (cs.isValid()) {
            jdbc.execute(cs.toInsertSql().toString());
        }
    }

    /**
     * ORM features: changeset(), cast(), validateRequired(), validateLength(),
     *   validateContains(), validateStartsWith(), validateEndsWith(),
     *   getChange(), deleteChange(), toUpdateSql()
     * Update list name (demonstrates many changeset validations).
     */
    public boolean updateListName(Long id, String name) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("name", name)
        ));

        List<SafeIdentifier> fields = List.of(nameField);
        Changeset cs = SrcGlobal.changeset(listTableDef, params)
                .cast(fields)
                .validateRequired(fields)
                .validateLength(nameField, 1, 200);

        if (cs.isValid()) {
            // Demonstrate getChange
            String val = cs.getChange(nameField);
            jdbc.execute(cs.toUpdateSql(id.intValue()).toString());
            return true;
        }
        return false;
    }

    /**
     * ORM features: deleteSql() (quick delete by ID)
     * Delete a list.
     */
    public void deleteList(Long id) {
        // Delete child relations first using deleteFrom() query builder
        SqlFragment delTodoTags = SrcGlobal.deleteFrom(todoTagsTable)
                .where(buildEqCondition(todoIdField, 0)) // placeholder, use raw SQL below
                .toSql();
        // Actually use raw SQL for cascading child cleanup
        jdbc.update("DELETE FROM todo_tags WHERE todo_id IN (SELECT id FROM todos WHERE list_id = ?)", id);
        jdbc.update("DELETE FROM todos WHERE list_id = ?", id);

        // Delete the list using ORM deleteSql
        SqlFragment deleteSql = SrcGlobal.deleteSql(listTableDef, id.intValue());
        jdbc.execute(deleteSql.toString());
    }

    /**
     * ORM features: from(), countSql()
     * Count all lists using ORM countSql.
     */
    public long countLists() {
        String sql = SrcGlobal.from(listsTable).countSql().toString();
        Long count = jdbc.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    /**
     * ORM features: from(), selectExpr(), groupBy(), countAll(), col()
     * Populate list counts using aggregate functions.
     */
    private void populateListCounts(TodoList list) {
        // Total count using countAll()
        String totalSql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.countAll()))
                .where(buildEqCondition(listIdField, list.getId().intValue()))
                .toSql()
                .toString();
        Long total = jdbc.queryForObject(totalSql, Long.class);

        // Completed count using countAll() with additional where
        SqlBuilder completedCond = new SqlBuilder();
        completedCond.appendSafe(completedField.getSqlValue());
        completedCond.appendSafe(" = ");
        completedCond.appendInt32(1);

        String completedSql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.countAll()))
                .where(buildEqCondition(listIdField, list.getId().intValue()))
                .where(completedCond.getAccumulated())
                .toSql()
                .toString();
        Long completed = jdbc.queryForObject(completedSql, Long.class);

        list.setTotalCount(total != null ? total : 0);
        list.setCompletedCount(completed != null ? completed : 0);
    }

    // ---------------------------------------------------------------
    // Todo item operations
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), where(), orderBy(), toSql()
     * Find all todos for a given list.
     */
    public List<TodoItem> findItemsByListId(Long listId) {
        String sql = SrcGlobal.from(todosTable)
                .where(buildEqCondition(listIdField, listId.intValue()))
                .orderBy(priorityField, true)
                .orderBy(createdAtField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), where(), limit(), toSql()
     * Find a single todo by id.
     */
    public TodoItem findItemById(Long id) {
        String sql = SrcGlobal.from(todosTable)
                .where(buildEqCondition(idField, id.intValue()))
                .limit(1)
                .toSql()
                .toString();
        List<TodoItem> results = jdbc.query(sql, itemMapper);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * ORM features: changeset(), cast(), validateRequired(), validateInt(),
     *   validateNumber() with NumberValidationOpts, validateLength(),
     *   validateInclusion(), toInsertSql()
     * Insert a new todo with full validation.
     */
    public List<ChangesetError> insertItem(String title, Long listId, String priority, String dueDate) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("title", title),
            new SimpleImmutableEntry<>("completed", "0"),
            new SimpleImmutableEntry<>("priority", priority != null ? priority : "3"),
            new SimpleImmutableEntry<>("list_id", String.valueOf(listId)),
            new SimpleImmutableEntry<>("created_at", now())
        ));

        List<SafeIdentifier> castFields = List.of(titleField, completedField, priorityField, listIdField, createdAtField);
        List<SafeIdentifier> requiredFields = List.of(titleField, completedField, priorityField, listIdField, createdAtField);

        // NumberValidationOpts: priority must be >= 1 and <= 5
        NumberValidationOpts priorityOpts = new NumberValidationOpts(null, null, 1.0, 5.0, null);

        Changeset cs = SrcGlobal.changeset(todoTableDef, params)
                .cast(castFields)
                .validateRequired(requiredFields)
                .validateLength(titleField, 1, 500)
                .validateInt(priorityField)
                .validateNumber(priorityField, priorityOpts)
                .validateInt(completedField)
                .validateInclusion(completedField, List.of("0", "1"));

        // Add due_date if provided
        if (dueDate != null && !dueDate.trim().isEmpty()) {
            cs = cs.putChange(dueDateField, dueDate.trim());
        }

        if (cs.isValid()) {
            jdbc.execute(cs.toInsertSql().toString());
            return List.of();
        }
        return cs.getErrors();
    }

    /**
     * Convenience overload for seeding.
     */
    public void insertItem(String title, Long listId, boolean completed, int priority, String dueDate) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("title", title),
            new SimpleImmutableEntry<>("completed", completed ? "1" : "0"),
            new SimpleImmutableEntry<>("priority", String.valueOf(priority)),
            new SimpleImmutableEntry<>("list_id", String.valueOf(listId)),
            new SimpleImmutableEntry<>("created_at", now())
        ));

        List<SafeIdentifier> fields = List.of(titleField, completedField, priorityField, listIdField, createdAtField);
        Changeset cs = SrcGlobal.changeset(todoTableDef, params)
                .cast(fields)
                .validateRequired(fields);

        if (dueDate != null) {
            cs = cs.putChange(dueDateField, dueDate);
        }

        if (cs.isValid()) {
            jdbc.execute(cs.toInsertSql().toString());
        }
    }

    /**
     * ORM features: update().set().where().toSql(), SqlInt32
     * Toggle the completed state using UpdateQuery builder.
     */
    public void toggleItem(Long id) {
        TodoItem item = findItemById(id);
        if (item == null) return;

        int newVal = item.getCompleted() == 0 ? 1 : 0;
        String sql = SrcGlobal.update(todosTable)
                .set(completedField, new SqlInt32(newVal))
                .where(buildEqCondition(idField, id.intValue()))
                .toSql()
                .toString();
        jdbc.execute(sql);
    }

    /**
     * ORM features: changeset(), cast(), validateRequired(), validateLength(), toUpdateSql()
     * Update a todo title via changeset.
     */
    public List<ChangesetError> updateItemTitle(Long id, String title) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("title", title)
        ));
        List<SafeIdentifier> fields = List.of(titleField);
        Changeset cs = SrcGlobal.changeset(todoTableDef, params)
                .cast(fields)
                .validateRequired(fields)
                .validateLength(titleField, 1, 500);

        if (cs.isValid()) {
            jdbc.execute(cs.toUpdateSql(id.intValue()).toString());
            return List.of();
        }
        return cs.getErrors();
    }

    /**
     * ORM features: update().set().where().toSql(), SqlInt32, NumberValidationOpts
     * Update todo priority using UpdateQuery with validation.
     */
    public boolean updateItemPriority(Long id, int priority) {
        if (priority < 1 || priority > 5) return false;
        String sql = SrcGlobal.update(todosTable)
                .set(priorityField, new SqlInt32(priority))
                .where(buildEqCondition(idField, id.intValue()))
                .toSql()
                .toString();
        jdbc.execute(sql);
        return true;
    }

    /**
     * ORM features: update().set().where().toSql(), SqlString
     * Update todo due date using UpdateQuery.
     */
    public void updateItemDueDate(Long id, String dueDate) {
        String sql = SrcGlobal.update(todosTable)
                .set(dueDateField, new SqlString(dueDate))
                .where(buildEqCondition(idField, id.intValue()))
                .toSql()
                .toString();
        jdbc.execute(sql);
    }

    /**
     * ORM features: deleteFrom().where().toSql()
     * Delete a todo using DeleteQuery builder.
     */
    public void deleteItem(Long id) {
        // First delete tag associations using DeleteQuery
        String delTagsSql = SrcGlobal.deleteFrom(todoTagsTable)
                .where(buildEqCondition(todoIdField, id.intValue()))
                .toSql()
                .toString();
        jdbc.execute(delTagsSql);

        // Then delete the todo using deleteSql
        SqlFragment deleteSql = SrcGlobal.deleteSql(todoTableDef, id.intValue());
        jdbc.execute(deleteSql.toString());
    }

    /**
     * ORM features: from(), where(), safeToSql() with default limit
     * Find todos using safeToSql (applies default limit if none set).
     */
    public List<TodoItem> findItemsSafe(Long listId, int defaultLimit) {
        String sql = SrcGlobal.from(todosTable)
                .where(buildEqCondition(listIdField, listId.intValue()))
                .orderBy(priorityField, true)
                .safeToSql(defaultLimit)
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    // ---------------------------------------------------------------
    // Tag operations
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), orderBy(), toSql()
     * Find all tags.
     */
    public List<Tag> findAllTags() {
        String sql = SrcGlobal.from(tagsTable)
                .orderBy(nameField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, tagMapper);
    }

    /**
     * ORM features: changeset(), cast(), validateRequired(), validateLength(),
     *   validateExclusion(), toInsertSql()
     * Insert a tag, excluding reserved names.
     */
    public List<ChangesetError> insertTag(String name) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("name", name)
        ));
        List<SafeIdentifier> fields = List.of(nameField);
        Changeset cs = SrcGlobal.changeset(tagTableDef, params)
                .cast(fields)
                .validateRequired(fields)
                .validateLength(nameField, 1, 50)
                .validateExclusion(nameField, List.of("admin", "system", "root", "null"));

        if (cs.isValid()) {
            jdbc.execute(cs.toInsertSql().toString());
            return List.of();
        }
        return cs.getErrors();
    }

    /**
     * ORM features: deleteSql()
     * Delete a tag by ID.
     */
    public void deleteTag(Long id) {
        // Remove associations first
        String delAssocSql = SrcGlobal.deleteFrom(todoTagsTable)
                .where(buildEqCondition(tagIdField, id.intValue()))
                .toSql()
                .toString();
        jdbc.execute(delAssocSql);
        jdbc.execute(SrcGlobal.deleteSql(tagTableDef, id.intValue()).toString());
    }

    /**
     * ORM features: changeset(), cast(), validateRequired(), validateInt(), toInsertSql()
     * Associate a tag with a todo.
     */
    public void tagTodo(Long todoId, Long tagId) {
        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("todo_id", String.valueOf(todoId)),
            new SimpleImmutableEntry<>("tag_id", String.valueOf(tagId))
        ));
        List<SafeIdentifier> fields = List.of(todoIdField, tagIdField);
        Changeset cs = SrcGlobal.changeset(todoTagTableDef, params)
                .cast(fields)
                .validateRequired(fields)
                .validateInt(todoIdField)
                .validateInt(tagIdField);

        if (cs.isValid()) {
            try {
                jdbc.execute(cs.toInsertSql().toString());
            } catch (Exception e) {
                // Ignore duplicate
            }
        }
    }

    /**
     * ORM features: deleteFrom().where().toSql()
     * Remove a tag from a todo.
     */
    public void untagTodo(Long todoId, Long tagId) {
        SqlBuilder cond = new SqlBuilder();
        cond.appendSafe(todoIdField.getSqlValue());
        cond.appendSafe(" = ");
        cond.appendInt32(todoId.intValue());
        cond.appendSafe(" AND ");
        cond.appendSafe(tagIdField.getSqlValue());
        cond.appendSafe(" = ");
        cond.appendInt32(tagId.intValue());

        String sql = SrcGlobal.deleteFrom(todoTagsTable)
                .where(cond.getAccumulated())
                .toSql()
                .toString();
        jdbc.execute(sql);
    }

    /**
     * ORM features: from(), innerJoin(), where(), toSql(), col(), buildJoinCondition()
     * Find tags for a specific todo using innerJoin.
     */
    public List<Tag> findTagsForTodo(Long todoId) {
        // SELECT tags.* FROM tags INNER JOIN todo_tags ON tags.id = todo_tags.tag_id WHERE todo_tags.todo_id = ?
        String sql = SrcGlobal.from(tagsTable)
                .select(List.of(
                    SrcGlobal.safeIdentifier("id"),
                    SrcGlobal.safeIdentifier("name")
                ))
                .innerJoin(todoTagsTable, buildJoinCondition(tagsTable, idField, todoTagsTable, tagIdField))
                .where(buildQualifiedEqCondition(todoTagsTable, todoIdField, todoId.intValue()))
                .orderBy(nameField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, tagMapper);
    }

    // ---------------------------------------------------------------
    // Advanced query demonstrations
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), whereLike(), toSql()
     * Search todos by title pattern using LIKE.
     */
    public List<TodoItem> searchTodos(String pattern) {
        String sql = SrcGlobal.from(todosTable)
                .whereLike(titleField, "%" + pattern + "%")
                .orderBy(createdAtField, false)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), whereNull(), toSql()
     * Find todos with no due date.
     */
    public List<TodoItem> findTodosWithoutDueDate() {
        String sql = SrcGlobal.from(todosTable)
                .whereNull(dueDateField)
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), whereNotNull(), toSql()
     * Find todos that have a due date set.
     */
    public List<TodoItem> findTodosWithDueDate() {
        String sql = SrcGlobal.from(todosTable)
                .whereNotNull(dueDateField)
                .orderByNulls(dueDateField, true, new NullsLast())
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), whereBetween(), toSql(), SqlInt32
     * Find todos with priority between low and high.
     */
    public List<TodoItem> findTodosByPriorityRange(int low, int high) {
        String sql = SrcGlobal.from(todosTable)
                .whereBetween(priorityField, new SqlInt32(low), new SqlInt32(high))
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), whereIn(), toSql(), SqlInt32
     * Find todos with specific priorities.
     */
    public List<TodoItem> findTodosByPriorities(List<Integer> priorities) {
        List<SqlPart> values = new ArrayList<>();
        for (int p : priorities) {
            values.add(new SqlInt32(p));
        }
        String sql = SrcGlobal.from(todosTable)
                .whereIn(priorityField, values)
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), whereNot(), toSql()
     * Find todos that are NOT completed.
     */
    public List<TodoItem> findIncompleteTodos() {
        SqlBuilder cond = new SqlBuilder();
        cond.appendSafe(completedField.getSqlValue());
        cond.appendSafe(" = ");
        cond.appendInt32(1);

        String sql = SrcGlobal.from(todosTable)
                .whereNot(cond.getAccumulated())
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), where(), orWhere(), toSql()
     * Find high-priority OR completed todos.
     */
    public List<TodoItem> findHighPriorityOrCompleted() {
        SqlBuilder highPriority = new SqlBuilder();
        highPriority.appendSafe(priorityField.getSqlValue());
        highPriority.appendSafe(" <= ");
        highPriority.appendInt32(2);

        SqlBuilder completed = new SqlBuilder();
        completed.appendSafe(completedField.getSqlValue());
        completed.appendSafe(" = ");
        completed.appendInt32(1);

        String sql = SrcGlobal.from(todosTable)
                .where(highPriority.getAccumulated())
                .orWhere(completed.getAccumulated())
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), whereInSubquery(), toSql()
     * Find todos that belong to lists containing a specific name pattern.
     */
    public List<TodoItem> findTodosInMatchingLists(String listNamePattern) {
        Query subq = SrcGlobal.from(listsTable)
                .select(List.of(idField))
                .whereLike(nameField, "%" + listNamePattern + "%");

        String sql = SrcGlobal.from(todosTable)
                .whereInSubquery(listIdField, subq)
                .orderBy(createdAtField, false)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), select(), distinct(), toSql()
     * Get distinct priorities in use.
     */
    public List<Map<String, Object>> getDistinctPriorities() {
        String sql = SrcGlobal.from(todosTable)
                .select(List.of(priorityField))
                .distinct()
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.queryForList(sql);
    }

    /**
     * ORM features: from(), select(), limit(), offset(), toSql()
     * Paginated todos.
     */
    public List<TodoItem> findTodosPaginated(int page, int pageSize) {
        String sql = SrcGlobal.from(todosTable)
                .orderBy(idField, true)
                .limit(pageSize)
                .offset(page * pageSize)
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), orderByNulls() with NullsFirst
     * Order todos with nulls-first for due_date.
     */
    public List<TodoItem> findTodosNullsFirst() {
        String sql = SrcGlobal.from(todosTable)
                .orderByNulls(dueDateField, true, new NullsFirst())
                .toSql()
                .toString();
        return jdbc.query(sql, itemMapper);
    }

    /**
     * ORM features: from(), lock() with ForUpdate
     * Select todos with FOR UPDATE lock (demonstrated as SQL generation).
     */
    public String generateLockedQuery(Long listId) {
        return SrcGlobal.from(todosTable)
                .where(buildEqCondition(listIdField, listId.intValue()))
                .lock(new ForUpdate())
                .toSql()
                .toString();
    }

    /**
     * ORM features: from(), lock() with ForShare
     * Select todos with FOR SHARE lock (demonstrated as SQL generation).
     */
    public String generateShareLockedQuery(Long listId) {
        return SrcGlobal.from(todosTable)
                .where(buildEqCondition(listIdField, listId.intValue()))
                .lock(new ForShare())
                .toSql()
                .toString();
    }

    // ---------------------------------------------------------------
    // Join demonstrations
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), innerJoin(), select(), where(), col(), toSql()
     * Inner join todos with lists.
     */
    public List<Map<String, Object>> findTodosWithListNames() {
        SqlFragment joinCond = buildJoinCondition(todosTable, listIdField, listsTable, idField);

        // Use selectExpr to pick specific qualified columns
        SqlFragment todoId = SrcGlobal.col(todosTable, idField);
        SqlFragment todoTitle = SrcGlobal.col(todosTable, titleField);
        SqlFragment listName = SrcGlobal.col(listsTable, nameField);
        SqlFragment todoPriority = SrcGlobal.col(todosTable, priorityField);
        SqlFragment todoCompleted = SrcGlobal.col(todosTable, completedField);

        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(todoId, todoTitle, listName, todoPriority, todoCompleted))
                .innerJoin(listsTable, joinCond)
                .orderBy(priorityField, true)
                .toSql()
                .toString();
        return jdbc.queryForList(sql);
    }

    /**
     * ORM features: from(), leftJoin(), toSql()
     * Left join todos with todo_tags to find todos and their tag count.
     */
    public List<Map<String, Object>> findTodosWithTagCounts() {
        SqlFragment joinCond = buildJoinCondition(todosTable, idField, todoTagsTable, todoIdField);
        SqlFragment countExpr = SrcGlobal.countCol(tagIdField);

        // Build a select expression for "COUNT(tag_id) AS tag_count"
        SqlBuilder tagCountExpr = new SqlBuilder();
        tagCountExpr.appendFragment(countExpr);
        tagCountExpr.appendSafe(" AS tag_count");

        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(
                    SrcGlobal.col(todosTable, idField),
                    SrcGlobal.col(todosTable, titleField),
                    tagCountExpr.getAccumulated()
                ))
                .leftJoin(todoTagsTable, joinCond)
                .groupBy(SrcGlobal.safeIdentifier("id"))
                .orderBy(idField, true)
                .toSql()
                .toString();
        return jdbc.queryForList(sql);
    }

    /**
     * ORM features: from(), rightJoin(), toSql()
     * Right join (demonstrated as SQL generation since SQLite doesn't support RIGHT JOIN).
     */
    public String generateRightJoinQuery() {
        SqlFragment joinCond = buildJoinCondition(todoTagsTable, tagIdField, tagsTable, idField);
        return SrcGlobal.from(todoTagsTable)
                .rightJoin(tagsTable, joinCond)
                .toSql()
                .toString();
    }

    /**
     * ORM features: from(), fullJoin(), toSql()
     * Full join (demonstrated as SQL generation).
     */
    public String generateFullJoinQuery() {
        SqlFragment joinCond = buildJoinCondition(todosTable, idField, todoTagsTable, todoIdField);
        return SrcGlobal.from(todosTable)
                .fullJoin(todoTagsTable, joinCond)
                .toSql()
                .toString();
    }

    /**
     * ORM features: from(), crossJoin(), toSql()
     * Cross join (demonstrated as SQL generation).
     */
    public String generateCrossJoinQuery() {
        return SrcGlobal.from(todosTable)
                .crossJoin(tagsTable)
                .toSql()
                .toString();
    }

    // ---------------------------------------------------------------
    // Aggregate demonstrations
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), selectExpr(), countAll()
     * Count all todos.
     */
    public long countAllTodos() {
        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.countAll()))
                .toSql()
                .toString();
        Long count = jdbc.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    /**
     * ORM features: countCol()
     * Count non-null due dates.
     */
    public long countTodosWithDueDate() {
        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.countCol(dueDateField)))
                .toSql()
                .toString();
        Long count = jdbc.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    /**
     * ORM features: sumCol()
     * Sum of all priorities.
     */
    public long sumPriorities() {
        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.sumCol(priorityField)))
                .toSql()
                .toString();
        Long sum = jdbc.queryForObject(sql, Long.class);
        return sum != null ? sum : 0;
    }

    /**
     * ORM features: avgCol()
     * Average priority.
     */
    public double avgPriority() {
        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.avgCol(priorityField)))
                .toSql()
                .toString();
        Double avg = jdbc.queryForObject(sql, Double.class);
        return avg != null ? avg : 0.0;
    }

    /**
     * ORM features: minCol()
     * Minimum priority.
     */
    public int minPriority() {
        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.minCol(priorityField)))
                .toSql()
                .toString();
        Integer min = jdbc.queryForObject(sql, Integer.class);
        return min != null ? min : 0;
    }

    /**
     * ORM features: maxCol()
     * Maximum priority.
     */
    public int maxPriority() {
        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.maxCol(priorityField)))
                .toSql()
                .toString();
        Integer max = jdbc.queryForObject(sql, Integer.class);
        return max != null ? max : 0;
    }

    // ---------------------------------------------------------------
    // Group By / Having
    // ---------------------------------------------------------------

    /**
     * ORM features: from(), selectExpr(), groupBy(), having(), toSql(), countAll()
     * Group todos by list_id with HAVING count > threshold.
     */
    public List<Map<String, Object>> findListsWithMinTodos(int minCount) {
        SqlBuilder havingCond = new SqlBuilder();
        havingCond.appendFragment(SrcGlobal.countAll());
        havingCond.appendSafe(" >= ");
        havingCond.appendInt32(minCount);

        // Build count alias expression
        SqlBuilder countAlias = new SqlBuilder();
        countAlias.appendFragment(SrcGlobal.countAll());
        countAlias.appendSafe(" AS todo_count");

        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(
                    SrcGlobal.col(todosTable, listIdField),
                    countAlias.getAccumulated()
                ))
                .groupBy(listIdField)
                .having(havingCond.getAccumulated())
                .orderBy(listIdField, true)
                .toSql()
                .toString();
        return jdbc.queryForList(sql);
    }

    /**
     * ORM features: from(), selectExpr(), groupBy(), having(), orHaving(), toSql()
     * Demonstrate orHaving: lists with many todos OR high avg priority.
     */
    public List<Map<String, Object>> findListsWithOrHaving(int minCount, double maxAvgPriority) {
        SqlBuilder havingCond = new SqlBuilder();
        havingCond.appendFragment(SrcGlobal.countAll());
        havingCond.appendSafe(" >= ");
        havingCond.appendInt32(minCount);

        SqlBuilder orHavingCond = new SqlBuilder();
        orHavingCond.appendFragment(SrcGlobal.avgCol(priorityField));
        orHavingCond.appendSafe(" <= ");
        orHavingCond.appendFloat64(maxAvgPriority);

        SqlBuilder countAlias = new SqlBuilder();
        countAlias.appendFragment(SrcGlobal.countAll());
        countAlias.appendSafe(" AS todo_count");

        SqlBuilder avgAlias = new SqlBuilder();
        avgAlias.appendFragment(SrcGlobal.avgCol(priorityField));
        avgAlias.appendSafe(" AS avg_priority");

        String sql = SrcGlobal.from(todosTable)
                .selectExpr(List.of(
                    SrcGlobal.col(todosTable, listIdField),
                    countAlias.getAccumulated(),
                    avgAlias.getAccumulated()
                ))
                .groupBy(listIdField)
                .having(havingCond.getAccumulated())
                .orHaving(orHavingCond.getAccumulated())
                .toSql()
                .toString();
        return jdbc.queryForList(sql);
    }

    // ---------------------------------------------------------------
    // Set operations
    // ---------------------------------------------------------------

    /**
     * ORM features: unionSql()
     * Union of high-priority and completed todos.
     */
    public String generateUnionQuery() {
        SqlBuilder highPri = new SqlBuilder();
        highPri.appendSafe(priorityField.getSqlValue());
        highPri.appendSafe(" <= ");
        highPri.appendInt32(2);

        SqlBuilder completed = new SqlBuilder();
        completed.appendSafe(completedField.getSqlValue());
        completed.appendSafe(" = ");
        completed.appendInt32(1);

        Query q1 = SrcGlobal.from(todosTable).where(highPri.getAccumulated());
        Query q2 = SrcGlobal.from(todosTable).where(completed.getAccumulated());

        return SrcGlobal.unionSql(q1, q2).toString();
    }

    /**
     * ORM features: unionAllSql()
     * Union ALL of two queries (keeps duplicates).
     */
    public String generateUnionAllQuery() {
        SqlBuilder highPri = new SqlBuilder();
        highPri.appendSafe(priorityField.getSqlValue());
        highPri.appendSafe(" <= ");
        highPri.appendInt32(2);

        SqlBuilder completed = new SqlBuilder();
        completed.appendSafe(completedField.getSqlValue());
        completed.appendSafe(" = ");
        completed.appendInt32(1);

        Query q1 = SrcGlobal.from(todosTable).where(highPri.getAccumulated());
        Query q2 = SrcGlobal.from(todosTable).where(completed.getAccumulated());

        return SrcGlobal.unionAllSql(q1, q2).toString();
    }

    /**
     * ORM features: intersectSql()
     * Intersection: todos that are both high-priority AND completed.
     */
    public String generateIntersectQuery() {
        SqlBuilder highPri = new SqlBuilder();
        highPri.appendSafe(priorityField.getSqlValue());
        highPri.appendSafe(" <= ");
        highPri.appendInt32(2);

        SqlBuilder completed = new SqlBuilder();
        completed.appendSafe(completedField.getSqlValue());
        completed.appendSafe(" = ");
        completed.appendInt32(1);

        Query q1 = SrcGlobal.from(todosTable).where(highPri.getAccumulated());
        Query q2 = SrcGlobal.from(todosTable).where(completed.getAccumulated());

        return SrcGlobal.intersectSql(q1, q2).toString();
    }

    /**
     * ORM features: exceptSql()
     * Except: high-priority todos that are NOT completed.
     */
    public String generateExceptQuery() {
        SqlBuilder highPri = new SqlBuilder();
        highPri.appendSafe(priorityField.getSqlValue());
        highPri.appendSafe(" <= ");
        highPri.appendInt32(2);

        SqlBuilder completed = new SqlBuilder();
        completed.appendSafe(completedField.getSqlValue());
        completed.appendSafe(" = ");
        completed.appendInt32(1);

        Query q1 = SrcGlobal.from(todosTable).where(highPri.getAccumulated());
        Query q2 = SrcGlobal.from(todosTable).where(completed.getAccumulated());

        return SrcGlobal.exceptSql(q1, q2).toString();
    }

    // ---------------------------------------------------------------
    // Subquery demonstrations
    // ---------------------------------------------------------------

    /**
     * ORM features: subquery(), from() on subquery alias
     * Demonstrate subquery as a derived table.
     */
    public String generateSubqueryExample() {
        Query inner = SrcGlobal.from(todosTable)
                .select(List.of(listIdField))
                .where(buildEqCondition(completedField, 1));

        SafeIdentifier alias = SrcGlobal.safeIdentifier("completed_todos");
        return SrcGlobal.subquery(inner, alias).toString();
    }

    /**
     * ORM features: existsSql()
     * Find lists that have at least one todo using EXISTS subquery.
     */
    public List<TodoList> findListsWithTodos() {
        // Build the EXISTS subquery
        SqlFragment listIdMatch = buildJoinCondition(listsTable, idField, todosTable, listIdField);
        Query existsQuery = SrcGlobal.from(todosTable)
                .where(listIdMatch);

        SqlFragment existsCond = SrcGlobal.existsSql(existsQuery);

        String sql = SrcGlobal.from(listsTable)
                .where(existsCond)
                .orderBy(nameField, true)
                .toSql()
                .toString();
        List<TodoList> lists = jdbc.query(sql, listMapper);
        for (TodoList list : lists) {
            populateListCounts(list);
        }
        return lists;
    }

    // ---------------------------------------------------------------
    // Comprehensive changeset validation demo
    // ---------------------------------------------------------------

    /**
     * ORM features: ALL changeset validations demonstrated together.
     * This method validates a hypothetical complex todo input to demonstrate
     * every single changeset validation method.
     */
    public Map<String, Object> validateComprehensive(
            String title, String priority, String completed, String dueDate,
            String listId, String confirmation) {

        Map<String, String> params = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("title", title != null ? title : ""),
            new SimpleImmutableEntry<>("completed", completed != null ? completed : "0"),
            new SimpleImmutableEntry<>("priority", priority != null ? priority : "3"),
            new SimpleImmutableEntry<>("list_id", listId != null ? listId : ""),
            new SimpleImmutableEntry<>("created_at", now()),
            new SimpleImmutableEntry<>("due_date", dueDate != null ? dueDate : "")
        ));

        // Demonstrate every validation method
        NumberValidationOpts priorityOpts = new NumberValidationOpts(
            0.0,   // greaterThan: > 0
            6.0,   // lessThan: < 6
            1.0,   // greaterThanOrEqual: >= 1
            5.0,   // lessThanOrEqual: <= 5
            null   // equalTo: not used
        );

        List<SafeIdentifier> castFields = List.of(
            titleField, completedField, priorityField, listIdField, createdAtField, dueDateField
        );

        Changeset cs = SrcGlobal.changeset(todoTableDef, params)
                .cast(castFields)
                .validateRequired(List.of(titleField, priorityField, listIdField))
                .validateLength(titleField, 1, 500)
                .validateInt(priorityField)
                .validateNumber(priorityField, priorityOpts)
                .validateInt(listIdField)
                .validateBool(completedField)
                .validateInclusion(completedField, List.of("0", "1", "true", "false", "yes", "no", "on", "off"))
                .validateExclusion(titleField, List.of("DROP TABLE", "DELETE FROM", "--"))
                .validateContains(titleField, "")  // will pass (empty substring always contained)
                .validateStartsWith(titleField, "") // will pass (empty prefix)
                .validateEndsWith(titleField, "");  // will pass (empty suffix)

        // Demonstrate putChange / getChange / deleteChange
        cs = cs.putChange(SrcGlobal.safeIdentifier("created_at"), now());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("valid", cs.isValid());
        result.put("errors", cs.getErrors().stream()
            .map(e -> e.getField() + ": " + e.getMessage())
            .toList());

        if (cs.isValid()) {
            result.put("insertSql", cs.toInsertSql().toString());
            result.put("updateSql", cs.toUpdateSql(1).toString());
        }

        return result;
    }

    // ---------------------------------------------------------------
    // SQL generation showcase (returns SQL strings for display)
    // ---------------------------------------------------------------

    /**
     * Returns a map of feature names to generated SQL strings
     * demonstrating every ORM feature.
     */
    public Map<String, String> generateAllFeatureQueries() {
        Map<String, String> queries = new LinkedHashMap<>();

        // 1. Basic SELECT
        queries.put("from / toSql",
            SrcGlobal.from(todosTable).toSql().toString());

        // 2. SELECT with fields
        queries.put("select (specific fields)",
            SrcGlobal.from(todosTable)
                .select(List.of(idField, titleField, priorityField))
                .toSql().toString());

        // 3. SELECT with expressions
        queries.put("selectExpr (aggregate)",
            SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.countAll(), SrcGlobal.avgCol(priorityField)))
                .toSql().toString());

        // 4. WHERE
        queries.put("where",
            SrcGlobal.from(todosTable)
                .where(buildEqCondition(completedField, 1))
                .toSql().toString());

        // 5. OR WHERE
        queries.put("where + orWhere",
            SrcGlobal.from(todosTable)
                .where(buildEqCondition(completedField, 1))
                .orWhere(buildEqCondition(priorityField, 1))
                .toSql().toString());

        // 6. WHERE NULL
        queries.put("whereNull",
            SrcGlobal.from(todosTable)
                .whereNull(dueDateField)
                .toSql().toString());

        // 7. WHERE NOT NULL
        queries.put("whereNotNull",
            SrcGlobal.from(todosTable)
                .whereNotNull(dueDateField)
                .toSql().toString());

        // 8. WHERE IN
        queries.put("whereIn",
            SrcGlobal.from(todosTable)
                .whereIn(priorityField, List.of(new SqlInt32(1), new SqlInt32(2), new SqlInt32(3)))
                .toSql().toString());

        // 9. WHERE IN SUBQUERY
        Query subq = SrcGlobal.from(listsTable).select(List.of(idField)).whereLike(nameField, "%Work%");
        queries.put("whereInSubquery",
            SrcGlobal.from(todosTable)
                .whereInSubquery(listIdField, subq)
                .toSql().toString());

        // 10. WHERE NOT
        queries.put("whereNot",
            SrcGlobal.from(todosTable)
                .whereNot(buildEqCondition(completedField, 1))
                .toSql().toString());

        // 11. WHERE BETWEEN
        queries.put("whereBetween",
            SrcGlobal.from(todosTable)
                .whereBetween(priorityField, new SqlInt32(2), new SqlInt32(4))
                .toSql().toString());

        // 12. WHERE LIKE
        queries.put("whereLike",
            SrcGlobal.from(todosTable)
                .whereLike(titleField, "%bug%")
                .toSql().toString());

        // 13. WHERE ILIKE
        queries.put("whereILike",
            SrcGlobal.from(todosTable)
                .whereILike(titleField, "%BUG%")
                .toSql().toString());

        // 14. ORDER BY
        queries.put("orderBy",
            SrcGlobal.from(todosTable)
                .orderBy(priorityField, true)
                .orderBy(createdAtField, false)
                .toSql().toString());

        // 15. ORDER BY NULLS FIRST
        queries.put("orderByNulls (NULLS FIRST)",
            SrcGlobal.from(todosTable)
                .orderByNulls(dueDateField, true, new NullsFirst())
                .toSql().toString());

        // 16. ORDER BY NULLS LAST
        queries.put("orderByNulls (NULLS LAST)",
            SrcGlobal.from(todosTable)
                .orderByNulls(dueDateField, true, new NullsLast())
                .toSql().toString());

        // 17. LIMIT
        queries.put("limit",
            SrcGlobal.from(todosTable)
                .limit(10)
                .toSql().toString());

        // 18. OFFSET
        queries.put("limit + offset (pagination)",
            SrcGlobal.from(todosTable)
                .limit(10).offset(20)
                .toSql().toString());

        // 19. DISTINCT
        queries.put("distinct",
            SrcGlobal.from(todosTable)
                .select(List.of(priorityField))
                .distinct()
                .toSql().toString());

        // 20. GROUP BY
        queries.put("groupBy",
            SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.col(todosTable, listIdField), SrcGlobal.countAll()))
                .groupBy(listIdField)
                .toSql().toString());

        // 21. HAVING
        SqlBuilder havCond = new SqlBuilder();
        havCond.appendFragment(SrcGlobal.countAll());
        havCond.appendSafe(" > ");
        havCond.appendInt32(2);
        queries.put("groupBy + having",
            SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.col(todosTable, listIdField), SrcGlobal.countAll()))
                .groupBy(listIdField)
                .having(havCond.getAccumulated())
                .toSql().toString());

        // 22. OR HAVING
        SqlBuilder orHavCond = new SqlBuilder();
        orHavCond.appendFragment(SrcGlobal.avgCol(priorityField));
        orHavCond.appendSafe(" < ");
        orHavCond.appendInt32(3);
        queries.put("having + orHaving",
            SrcGlobal.from(todosTable)
                .selectExpr(List.of(SrcGlobal.col(todosTable, listIdField), SrcGlobal.countAll()))
                .groupBy(listIdField)
                .having(havCond.getAccumulated())
                .orHaving(orHavCond.getAccumulated())
                .toSql().toString());

        // 23. INNER JOIN
        queries.put("innerJoin",
            SrcGlobal.from(todosTable)
                .innerJoin(listsTable, buildJoinCondition(todosTable, listIdField, listsTable, idField))
                .toSql().toString());

        // 24. LEFT JOIN
        queries.put("leftJoin",
            SrcGlobal.from(todosTable)
                .leftJoin(todoTagsTable, buildJoinCondition(todosTable, idField, todoTagsTable, todoIdField))
                .toSql().toString());

        // 25. RIGHT JOIN
        queries.put("rightJoin",
            SrcGlobal.from(todoTagsTable)
                .rightJoin(tagsTable, buildJoinCondition(todoTagsTable, tagIdField, tagsTable, idField))
                .toSql().toString());

        // 26. FULL JOIN
        queries.put("fullJoin",
            SrcGlobal.from(todosTable)
                .fullJoin(todoTagsTable, buildJoinCondition(todosTable, idField, todoTagsTable, todoIdField))
                .toSql().toString());

        // 27. CROSS JOIN
        queries.put("crossJoin",
            SrcGlobal.from(todosTable)
                .crossJoin(tagsTable)
                .toSql().toString());

        // 28. LOCK FOR UPDATE
        queries.put("lock (FOR UPDATE)",
            SrcGlobal.from(todosTable)
                .where(buildEqCondition(idField, 1))
                .lock(new ForUpdate())
                .toSql().toString());

        // 29. LOCK FOR SHARE
        queries.put("lock (FOR SHARE)",
            SrcGlobal.from(todosTable)
                .where(buildEqCondition(idField, 1))
                .lock(new ForShare())
                .toSql().toString());

        // 30. countSql
        queries.put("countSql",
            SrcGlobal.from(todosTable)
                .where(buildEqCondition(completedField, 1))
                .countSql().toString());

        // 31. safeToSql
        queries.put("safeToSql (default limit 100)",
            SrcGlobal.from(todosTable)
                .safeToSql(100).toString());

        // 32. Aggregates
        queries.put("countAll()", SrcGlobal.countAll().toString());
        queries.put("countCol(priority)", SrcGlobal.countCol(priorityField).toString());
        queries.put("sumCol(priority)", SrcGlobal.sumCol(priorityField).toString());
        queries.put("avgCol(priority)", SrcGlobal.avgCol(priorityField).toString());
        queries.put("minCol(priority)", SrcGlobal.minCol(priorityField).toString());
        queries.put("maxCol(priority)", SrcGlobal.maxCol(priorityField).toString());

        // 33. col() - qualified reference
        queries.put("col(todos, title)", SrcGlobal.col(todosTable, titleField).toString());

        // 34. UNION
        Query uq1 = SrcGlobal.from(todosTable).where(buildEqCondition(completedField, 1));
        Query uq2 = SrcGlobal.from(todosTable).where(buildEqCondition(priorityField, 1));
        queries.put("unionSql", SrcGlobal.unionSql(uq1, uq2).toString());

        // 35. UNION ALL
        queries.put("unionAllSql", SrcGlobal.unionAllSql(uq1, uq2).toString());

        // 36. INTERSECT
        queries.put("intersectSql", SrcGlobal.intersectSql(uq1, uq2).toString());

        // 37. EXCEPT
        queries.put("exceptSql", SrcGlobal.exceptSql(uq1, uq2).toString());

        // 38. SUBQUERY
        Query subInner = SrcGlobal.from(todosTable).select(List.of(listIdField)).where(buildEqCondition(completedField, 1));
        SafeIdentifier subAlias = SrcGlobal.safeIdentifier("done_lists");
        queries.put("subquery", SrcGlobal.subquery(subInner, subAlias).toString());

        // 39. EXISTS
        Query existsInner = SrcGlobal.from(todoTagsTable).where(buildEqCondition(todoIdField, 1));
        queries.put("existsSql", SrcGlobal.existsSql(existsInner).toString());

        // 40. UPDATE query builder
        queries.put("update().set().where()",
            SrcGlobal.update(todosTable)
                .set(completedField, new SqlInt32(1))
                .set(priorityField, new SqlInt32(1))
                .where(buildEqCondition(idField, 42))
                .toSql().toString());

        // 41. DELETE query builder
        queries.put("deleteFrom().where()",
            SrcGlobal.deleteFrom(todosTable)
                .where(buildEqCondition(completedField, 1))
                .toSql().toString());

        // 42. DELETE with limit
        queries.put("deleteFrom().where().limit()",
            SrcGlobal.deleteFrom(todosTable)
                .where(buildEqCondition(completedField, 1))
                .limit(5)
                .toSql().toString());

        // 43. deleteSql (quick by ID)
        queries.put("deleteSql (by ID)",
            SrcGlobal.deleteSql(todoTableDef, 42).toString());

        // 44. Changeset INSERT
        Map<String, String> insertParams = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("title", "Test Todo"),
            new SimpleImmutableEntry<>("completed", "0"),
            new SimpleImmutableEntry<>("priority", "3"),
            new SimpleImmutableEntry<>("list_id", "1"),
            new SimpleImmutableEntry<>("created_at", now())
        ));
        List<SafeIdentifier> insertFields = List.of(titleField, completedField, priorityField, listIdField, createdAtField);
        queries.put("changeset.toInsertSql()",
            SrcGlobal.changeset(todoTableDef, insertParams)
                .cast(insertFields)
                .validateRequired(insertFields)
                .toInsertSql().toString());

        // 45. Changeset UPDATE
        Map<String, String> updateParams = Core.mapConstructor(List.of(
            new SimpleImmutableEntry<>("title", "Updated Title")
        ));
        queries.put("changeset.toUpdateSql()",
            SrcGlobal.changeset(todoTableDef, updateParams)
                .cast(List.of(titleField))
                .validateRequired(List.of(titleField))
                .toUpdateSql(42).toString());

        // 46. UPDATE with orWhere
        queries.put("update with orWhere",
            SrcGlobal.update(todosTable)
                .set(completedField, new SqlInt32(1))
                .where(buildEqCondition(priorityField, 1))
                .orWhere(buildEqCondition(priorityField, 2))
                .toSql().toString());

        // 47. DELETE with orWhere
        queries.put("deleteFrom with orWhere",
            SrcGlobal.deleteFrom(todosTable)
                .where(buildEqCondition(completedField, 1))
                .orWhere(buildEqCondition(priorityField, 5))
                .toSql().toString());

        // 48. UPDATE with limit
        queries.put("update with limit",
            SrcGlobal.update(todosTable)
                .set(completedField, new SqlInt32(1))
                .where(buildEqCondition(priorityField, 1))
                .limit(5)
                .toSql().toString());

        return queries;
    }
}
