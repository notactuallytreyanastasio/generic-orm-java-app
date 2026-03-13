package com.orm.todo.model;

/**
 * Plain POJO for a todo list. No JPA annotations -- mapped via JDBC + ORM.
 */
public class TodoList {

    private Long id;
    private String name;
    private String description;
    private String createdAt;

    // Derived counts populated by the repository layer
    private long completedCount;
    private long totalCount;

    public TodoList() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public long getCompletedCount() { return completedCount; }
    public void setCompletedCount(long completedCount) { this.completedCount = completedCount; }

    public long getTotalCount() { return totalCount; }
    public void setTotalCount(long totalCount) { this.totalCount = totalCount; }
}
