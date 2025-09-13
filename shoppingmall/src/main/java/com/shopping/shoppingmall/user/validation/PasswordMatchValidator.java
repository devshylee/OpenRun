package com.shopping.shoppingmall.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // BeanWrapperImpl을 사용하여 동적으로 필드 값을 가져옴
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(passwordField);
        Object confirmPasswordValue = new BeanWrapperImpl(value).getPropertyValue(confirmPasswordField);

        // 두 필드 값이 null이 아니고, 같은지 비교
        boolean isValid = (passwordValue != null) && passwordValue.equals(confirmPasswordValue);

        // 유효성 검사 실패 시 기본 에러 메시지 비활성화 및 커스텀 에러 메시지 설정
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                   .addPropertyNode(confirmPasswordField)
                   .addConstraintViolation();
        }

        return isValid;
    }
}