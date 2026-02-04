package com.openrun.common.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(String message) {
        super(message);
    }
}
