package com.openrun.common.exception;

/**
 * 현재 비밀번호가 일치하지 않을 때 발생하는 예외
 */
public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException() {
        super("현재 비밀번호가 올바르지 않습니다.");
    }
}
