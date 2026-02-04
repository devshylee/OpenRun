package com.openrun.api.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;

import com.openrun.common.dto.ErrorResponse;
import com.openrun.common.exception.DuplicateUserIdException;
import com.openrun.common.exception.EmailNotVerifiedException;
import com.openrun.common.exception.IncorrectPasswordException;
import com.openrun.common.exception.PasswordMismatchException;
import com.openrun.common.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Validation 에러 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", "입력값 검증에 실패했습니다.");

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addFieldError(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 중복 아이디 예외 처리
     */
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserIdException(DuplicateUserIdException ex) {
        ErrorResponse errorResponse = new ErrorResponse("DUPLICATE_USER_ID", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * 이메일 미인증 예외 처리
     */
    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotVerifiedException(EmailNotVerifiedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("EMAIL_NOT_VERIFIED", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * 현재 비밀번호 불일치 예외 처리
     */
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse("INCORRECT_PASSWORD", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 비밀번호 불일치 예외 처리
     */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse("PASSWORD_MISMATCH", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 사용자 미발견 예외 처리
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * 인증 실패 (로그인) 예외 처리
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("AUTHENTICATION_FAILED", "아이디 또는 비밀번호가 일치하지 않습니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * 기타 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");
        log.error("예상치 못한 예외 발생", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
