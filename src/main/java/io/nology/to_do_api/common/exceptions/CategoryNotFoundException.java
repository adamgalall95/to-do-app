package io.nology.to_do_api.common.exceptions;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(Long id) {
        super("Category with id " + id + " was not found");
    }
}