package io.nology.to_do_api.todos.dtos;

public class UpdateTodoDTO {

    private String task;

    private Long categoryId;

    private Boolean completed;

    public UpdateTodoDTO() {
    }

    public String getTask() {
        return task;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}