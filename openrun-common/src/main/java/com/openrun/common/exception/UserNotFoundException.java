package com.openrun.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }
}
