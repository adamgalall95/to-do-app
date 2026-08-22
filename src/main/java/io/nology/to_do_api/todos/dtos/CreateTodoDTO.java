package io.nology.to_do_api.todos.dtos;

import jakarta.validation.constraints.NotNull;

public class CreateTodoDTO {

    @NotNull
    private String task;

    @NotNull
    private Long categoryId;

    public CreateTodoDTO() {
    }

    public String getTask() {
        return task;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}