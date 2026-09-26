package io.nology.to_do_api.common.exceptions;

public class DuplicateCategoryException extends RuntimeException {

    public DuplicateCategoryException(String categoryName) {
        super("Category '" + categoryName + "' already exists");
    }
}
