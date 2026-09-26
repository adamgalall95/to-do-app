package io.nology.to_do_api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.nology.to_do_api.common.dto.ApiErrorResponse;
import io.nology.to_do_api.common.exceptions.DuplicateCategoryException;
import io.nology.to_do_api.common.exceptions.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
                        MethodArgumentTypeMismatchException ex,
                        HttpServletRequest req) {

                ApiErrorResponse response = ApiErrorResponse.of(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage(),
                                req.getRequestURI());

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNotFoundException(
                        NotFoundException ex,
                        HttpServletRequest req) {

                ApiErrorResponse response = ApiErrorResponse.of(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage(),
                                req.getRequestURI());

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(DuplicateCategoryException.class)
        public ResponseEntity<ApiErrorResponse> handleDuplicateCategoryException(
                        DuplicateCategoryException ex,
                        HttpServletRequest req) {

                ApiErrorResponse response = ApiErrorResponse.of(
                                HttpStatus.CONFLICT,
                                ex.getMessage(),
                                req.getRequestURI());

                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest req) {

                ApiErrorResponse response = ApiErrorResponse.of(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage(),
                                req.getRequestURI());

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
}