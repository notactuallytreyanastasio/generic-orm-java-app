package com.orm.todo.model;

/**
 * Plain POJO for a tag. No JPA annotations -- mapped via JDBC + ORM.
 */
public class Tag {

    private Long id;
    private String name;

    // Derived
    private int todoCount;

    public Tag() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getTodoCount() { return todoCount; }
    public void setTodoCount(int todoCount) { this.todoCount = todoCount; }
}
