package io.nology.to_do_api.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.nology.to_do_api.todos.entities.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTodoNotFound(
            TodoNotFoundException error) {

        ErrorResponse response = new ErrorResponse(
                error.getMessage(),
                404);

        return ResponseEntity
                .status(404)
                .body(response);
    }

}