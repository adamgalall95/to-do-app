package io.nology.to_do_api.todos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String task;

    @Column(name = "category_id")
    private long categoryId;

    @Column
    private boolean completed;

    public Todo() {
    }

    public long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}