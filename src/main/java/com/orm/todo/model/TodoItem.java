package com.orm.todo.model;

import java.time.LocalDateTime;

/**
 * Plain POJO for a todo item. No JPA annotations -- mapped via JDBC + ORM.
 */
public class TodoItem {

    private Long id;
    private String title;
    private int completed;
    private int priority;
    private String dueDate;
    private Long listId;
    private String createdAt;

    // Derived fields populated by joins/queries
    private String listName;
    private int tagCount;

    public TodoItem() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCompleted() { return completed; }
    public void setCompleted(int completed) { this.completed = completed; }
    public boolean isCompleted() { return completed != 0; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public Long getListId() { return listId; }
    public void setListId(Long listId) { this.listId = listId; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getListName() { return listName; }
    public void setListName(String listName) { this.listName = listName; }

    public int getTagCount() { return tagCount; }
    public void setTagCount(int tagCount) { this.tagCount = tagCount; }
}
