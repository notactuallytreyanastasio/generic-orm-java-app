package com.orm.todo.controller;

import com.orm.todo.model.Tag;
import com.orm.todo.model.TodoItem;
import com.orm.todo.model.TodoList;
import com.orm.todo.repository.TodoRepository;
import orm.src.ChangesetError;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class TodoController {

    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    // ---------------------------------------------------------------
    // Lists
    // ---------------------------------------------------------------

    /** ORM: from(), orderBy(), toSql(), countAll() */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("lists", repo.findAllLists());
        model.addAttribute("totalTodos", repo.countAllTodos());
        return "index";
    }

    /** ORM: changeset(), cast(), validateRequired(), validateLength(), putChange(), toInsertSql() */
    @PostMapping("/lists")
    public String createList(@RequestParam String name,
                             @RequestParam(required = false) String description,
                             RedirectAttributes flash) {
        if (name != null && !name.trim().isEmpty()) {
            repo.insertList(name.trim(), description);
        }
        return "redirect:/";
    }

    /** ORM: deleteSql(), deleteFrom().where().toSql() */
    @PostMapping("/lists/{id}/delete")
    public String deleteList(@PathVariable Long id) {
        repo.deleteList(id);
        return "redirect:/";
    }

    // ---------------------------------------------------------------
    // Single List with Todos
    // ---------------------------------------------------------------

    /** ORM: from(), where(), limit(), orderBy(), toSql() */
    @GetMapping("/lists/{id}")
    public String showList(@PathVariable Long id, Model model) {
        TodoList list = repo.findListById(id);
        if (list == null) return "redirect:/";
        model.addAttribute("list", list);
        model.addAttribute("todos", repo.findItemsByListId(id));
        model.addAttribute("allTags", repo.findAllTags());
        return "list";
    }

    /** ORM: changeset(), cast(), validateRequired(), validateInt(), validateNumber(),
     *       validateLength(), validateInclusion(), putChange(), toInsertSql() */
    @PostMapping("/lists/{id}/todos")
    public String addTodo(@PathVariable Long id,
                          @RequestParam String title,
                          @RequestParam(required = false) String priority,
                          @RequestParam(required = false) String dueDate,
                          RedirectAttributes flash) {
        TodoList list = repo.findListById(id);
        if (list != null && title != null && !title.trim().isEmpty()) {
            List<ChangesetError> errors = repo.insertItem(title.trim(), id, priority, dueDate);
            if (!errors.isEmpty()) {
                flash.addFlashAttribute("errors", errors.stream()
                    .map(e -> e.getField() + ": " + e.getMessage()).toList());
            }
        }
        return "redirect:/lists/" + id;
    }

    // ---------------------------------------------------------------
    // Todo Actions
    // ---------------------------------------------------------------

    /** ORM: update().set().where().toSql(), SqlInt32 */
    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        TodoItem item = repo.findItemById(id);
        if (item != null) {
            repo.toggleItem(id);
            return "redirect:/lists/" + item.getListId();
        }
        return "redirect:/";
    }

    /** ORM: changeset(), cast(), validateRequired(), validateLength(), toUpdateSql() */
    @PostMapping("/todos/{id}/edit")
    public String editTodo(@PathVariable Long id, @RequestParam String title, RedirectAttributes flash) {
        TodoItem item = repo.findItemById(id);
        if (item != null && title != null && !title.trim().isEmpty()) {
            List<ChangesetError> errors = repo.updateItemTitle(id, title.trim());
            if (!errors.isEmpty()) {
                flash.addFlashAttribute("errors", errors.stream()
                    .map(e -> e.getField() + ": " + e.getMessage()).toList());
            }
            return "redirect:/lists/" + item.getListId();
        }
        return "redirect:/";
    }

    /** ORM: update().set().where().toSql(), SqlInt32 */
    @PostMapping("/todos/{id}/priority")
    public String updatePriority(@PathVariable Long id, @RequestParam int priority) {
        TodoItem item = repo.findItemById(id);
        if (item != null) {
            repo.updateItemPriority(id, priority);
            return "redirect:/lists/" + item.getListId();
        }
        return "redirect:/";
    }

    /** ORM: deleteSql(), deleteFrom().where().toSql() */
    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        TodoItem item = repo.findItemById(id);
        if (item != null) {
            Long listId = item.getListId();
            repo.deleteItem(id);
            return "redirect:/lists/" + listId;
        }
        return "redirect:/";
    }

    // ---------------------------------------------------------------
    // Tags
    // ---------------------------------------------------------------

    /** ORM: from(), orderBy(), toSql() + changeset validations */
    @GetMapping("/tags")
    public String tagsPage(Model model) {
        model.addAttribute("tags", repo.findAllTags());
        return "tags";
    }

    /** ORM: changeset(), cast(), validateRequired(), validateLength(),
     *       validateExclusion(), toInsertSql() */
    @PostMapping("/tags")
    public String createTag(@RequestParam String name, RedirectAttributes flash) {
        if (name != null && !name.trim().isEmpty()) {
            List<ChangesetError> errors = repo.insertTag(name.trim().toLowerCase());
            if (!errors.isEmpty()) {
                flash.addFlashAttribute("errors", errors.stream()
                    .map(e -> e.getField() + ": " + e.getMessage()).toList());
            }
        }
        return "redirect:/tags";
    }

    /** ORM: deleteSql(), deleteFrom() */
    @PostMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id) {
        repo.deleteTag(id);
        return "redirect:/tags";
    }

    /** ORM: changeset(), validateRequired(), validateInt(), toInsertSql() */
    @PostMapping("/todos/{todoId}/tags/{tagId}")
    public String tagTodo(@PathVariable Long todoId, @PathVariable Long tagId) {
        TodoItem item = repo.findItemById(todoId);
        if (item != null) {
            repo.tagTodo(todoId, tagId);
            return "redirect:/lists/" + item.getListId();
        }
        return "redirect:/";
    }

    /** ORM: deleteFrom().where().toSql() */
    @PostMapping("/todos/{todoId}/tags/{tagId}/remove")
    public String untagTodo(@PathVariable Long todoId, @PathVariable Long tagId) {
        TodoItem item = repo.findItemById(todoId);
        if (item != null) {
            repo.untagTodo(todoId, tagId);
            return "redirect:/lists/" + item.getListId();
        }
        return "redirect:/";
    }

    // ---------------------------------------------------------------
    // Search (whereLike, whereNull, whereNotNull, whereBetween, etc.)
    // ---------------------------------------------------------------

    /** ORM: whereLike(), whereNull(), whereNotNull(), whereBetween(), whereIn(),
     *       whereNot(), where()+orWhere(), whereInSubquery(), distinct(), limit(), offset(),
     *       orderByNulls(), safeToSql() */
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q,
                         @RequestParam(required = false) String filter,
                         Model model) {
        List<TodoItem> results = List.of();
        String description = "";

        if (q != null && !q.trim().isEmpty()) {
            results = repo.searchTodos(q.trim());
            description = "Search: \"" + q + "\"";
        } else if (filter != null) {
            switch (filter) {
                case "no_due_date":
                    results = repo.findTodosWithoutDueDate();
                    description = "Todos without due date (whereNull)";
                    break;
                case "has_due_date":
                    results = repo.findTodosWithDueDate();
                    description = "Todos with due date (whereNotNull + orderByNulls)";
                    break;
                case "high_priority":
                    results = repo.findTodosByPriorityRange(1, 2);
                    description = "Priority 1-2 (whereBetween)";
                    break;
                case "medium_priority":
                    results = repo.findTodosByPriorities(List.of(3));
                    description = "Priority 3 (whereIn)";
                    break;
                case "incomplete":
                    results = repo.findIncompleteTodos();
                    description = "Incomplete todos (whereNot)";
                    break;
                case "high_or_done":
                    results = repo.findHighPriorityOrCompleted();
                    description = "High priority OR completed (where + orWhere)";
                    break;
                case "work_list":
                    results = repo.findTodosInMatchingLists("Work");
                    description = "In lists matching 'Work' (whereInSubquery)";
                    break;
                case "page2":
                    results = repo.findTodosPaginated(1, 5);
                    description = "Page 2, 5 per page (limit + offset)";
                    break;
                case "nulls_first":
                    results = repo.findTodosNullsFirst();
                    description = "Order by due_date NULLS FIRST (orderByNulls)";
                    break;
                case "safe_limit":
                    results = repo.findItemsSafe(1L, 3);
                    description = "List 1 with safeToSql(3) default limit";
                    break;
                default:
                    break;
            }
        }

        model.addAttribute("results", results);
        model.addAttribute("description", description);
        model.addAttribute("query", q);
        model.addAttribute("filter", filter);
        return "search";
    }

    // ---------------------------------------------------------------
    // Advanced: Joins, Aggregates, Set Ops, Subqueries
    // ---------------------------------------------------------------

    /** ORM: ALL features - innerJoin, leftJoin, rightJoin, fullJoin, crossJoin,
     *       countAll, countCol, sumCol, avgCol, minCol, maxCol,
     *       groupBy, having, orHaving,
     *       unionSql, unionAllSql, intersectSql, exceptSql,
     *       subquery, existsSql,
     *       lock(ForUpdate), lock(ForShare),
     *       col(), safeIdentifier() */
    @GetMapping("/advanced")
    public String advanced(Model model) {
        // Aggregate stats
        model.addAttribute("totalTodos", repo.countAllTodos());
        model.addAttribute("todosWithDueDate", repo.countTodosWithDueDate());
        model.addAttribute("sumPriorities", repo.sumPriorities());
        model.addAttribute("avgPriority", String.format("%.2f", repo.avgPriority()));
        model.addAttribute("minPriority", repo.minPriority());
        model.addAttribute("maxPriority", repo.maxPriority());

        // Joins
        model.addAttribute("todosWithListNames", repo.findTodosWithListNames());
        model.addAttribute("todosWithTagCounts", repo.findTodosWithTagCounts());

        // Group By / Having
        model.addAttribute("listsWithMinTodos", repo.findListsWithMinTodos(3));
        model.addAttribute("listsWithOrHaving", repo.findListsWithOrHaving(5, 2.5));

        // Lists with todos (EXISTS subquery)
        model.addAttribute("listsWithTodos", repo.findListsWithTodos());

        // Distinct priorities
        model.addAttribute("distinctPriorities", repo.getDistinctPriorities());

        // Generated SQL for all features
        model.addAttribute("allQueries", repo.generateAllFeatureQueries());

        // Generated join SQL (for display)
        model.addAttribute("rightJoinSql", repo.generateRightJoinQuery());
        model.addAttribute("fullJoinSql", repo.generateFullJoinQuery());
        model.addAttribute("crossJoinSql", repo.generateCrossJoinQuery());

        // Lock queries (for display)
        model.addAttribute("forUpdateSql", repo.generateLockedQuery(1L));
        model.addAttribute("forShareSql", repo.generateShareLockedQuery(1L));

        // Set operations (for display)
        model.addAttribute("unionSql", repo.generateUnionQuery());
        model.addAttribute("unionAllSql", repo.generateUnionAllQuery());
        model.addAttribute("intersectSql", repo.generateIntersectQuery());
        model.addAttribute("exceptSql", repo.generateExceptQuery());

        // Subquery
        model.addAttribute("subquerySql", repo.generateSubqueryExample());

        // Changeset validation demo
        model.addAttribute("validationResult",
            repo.validateComprehensive("Test Todo", "3", "0", "2026-04-01", "1", ""));

        return "advanced";
    }
}
