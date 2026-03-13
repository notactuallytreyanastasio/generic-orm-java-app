package com.orm.todo;

import com.orm.todo.repository.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final TodoRepository repo;
    private final JdbcTemplate jdbc;

    public DataLoader(TodoRepository repo, JdbcTemplate jdbc) {
        this.repo = repo;
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) {
        if (repo.countLists() > 0) {
            return;
        }

        // Seed list 1: Work Tasks
        repo.insertList("Work Tasks", "Things to do at work");
        Long workId = jdbc.queryForObject(
            "SELECT id FROM lists WHERE name = 'Work Tasks' ORDER BY id DESC LIMIT 1", Long.class);

        repo.insertItem("Review pull requests", workId, false, 1, "2026-03-20");
        repo.insertItem("Update project documentation", workId, false, 2, "2026-03-25");
        repo.insertItem("Fix login page bug", workId, false, 1, "2026-03-15");
        repo.insertItem("Set up CI pipeline", workId, true, 3, null);
        repo.insertItem("Refactor database layer", workId, false, 4, "2026-04-01");
        repo.insertItem("Write unit tests", workId, true, 2, null);

        // Seed list 2: Groceries
        repo.insertList("Groceries", null);
        Long groceriesId = jdbc.queryForObject(
            "SELECT id FROM lists WHERE name = 'Groceries' ORDER BY id DESC LIMIT 1", Long.class);

        repo.insertItem("Milk", groceriesId, false, 3, null);
        repo.insertItem("Eggs", groceriesId, false, 3, null);
        repo.insertItem("Bread", groceriesId, false, 2, null);
        repo.insertItem("Coffee beans", groceriesId, true, 1, null);
        repo.insertItem("Butter", groceriesId, true, 4, null);

        // Seed list 3: Personal Projects
        repo.insertList("Personal Projects", "Side projects and learning");
        Long personalId = jdbc.queryForObject(
            "SELECT id FROM lists WHERE name = 'Personal Projects' ORDER BY id DESC LIMIT 1", Long.class);

        repo.insertItem("Learn Rust", personalId, false, 2, "2026-06-01");
        repo.insertItem("Build a CLI tool", personalId, false, 3, "2026-05-15");
        repo.insertItem("Read Design Patterns book", personalId, true, 5, null);
        repo.insertItem("Contribute to open source", personalId, false, 4, null);

        // Seed tags
        repo.insertTag("urgent");
        repo.insertTag("bug");
        repo.insertTag("feature");
        repo.insertTag("documentation");
        repo.insertTag("learning");

        // Get tag IDs
        Long urgentId = jdbc.queryForObject("SELECT id FROM tags WHERE name = 'urgent'", Long.class);
        Long bugId = jdbc.queryForObject("SELECT id FROM tags WHERE name = 'bug'", Long.class);
        Long featureId = jdbc.queryForObject("SELECT id FROM tags WHERE name = 'feature'", Long.class);
        Long docId = jdbc.queryForObject("SELECT id FROM tags WHERE name = 'documentation'", Long.class);
        Long learnId = jdbc.queryForObject("SELECT id FROM tags WHERE name = 'learning'", Long.class);

        // Get todo IDs and tag them
        Long reviewPrId = jdbc.queryForObject(
            "SELECT id FROM todos WHERE title = 'Review pull requests'", Long.class);
        Long fixBugId = jdbc.queryForObject(
            "SELECT id FROM todos WHERE title = 'Fix login page bug'", Long.class);
        Long updateDocId = jdbc.queryForObject(
            "SELECT id FROM todos WHERE title = 'Update project documentation'", Long.class);
        Long learnRustId = jdbc.queryForObject(
            "SELECT id FROM todos WHERE title = 'Learn Rust'", Long.class);
        Long refactorId = jdbc.queryForObject(
            "SELECT id FROM todos WHERE title = 'Refactor database layer'", Long.class);

        repo.tagTodo(reviewPrId, featureId);
        repo.tagTodo(fixBugId, urgentId);
        repo.tagTodo(fixBugId, bugId);
        repo.tagTodo(updateDocId, docId);
        repo.tagTodo(learnRustId, learnId);
        repo.tagTodo(refactorId, featureId);
        repo.tagTodo(refactorId, urgentId);
    }
}
