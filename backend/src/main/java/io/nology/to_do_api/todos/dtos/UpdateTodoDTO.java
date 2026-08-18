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
}