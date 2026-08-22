package io.nology.to_do_api.categories.dtos;

import jakarta.validation.constraints.NotNull;

public class CreateCategoryDTO {

    @NotNull
    private String categoryName;

    public CreateCategoryDTO() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
