package io.nology.to_do_api.todos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTodoDTO {

    @NotBlank
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

    public void setTask(String task) {
        this.task = task;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}