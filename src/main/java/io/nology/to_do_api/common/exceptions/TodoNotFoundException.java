package io.nology.to_do_api.common.exceptions;

public class TodoNotFoundException extends NotFoundException {

    public TodoNotFoundException(Long id) {
        super("Todo with id " + id + " was not found");
    }
}