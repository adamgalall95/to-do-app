package io.nology.to_do_api.common.exceptions;

public class UnprocessableContentException extends RuntimeException {

    public UnprocessableContentException(String message) {
        super(message);
    }
}
