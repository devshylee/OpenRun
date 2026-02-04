package com.openrun.common.validation;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmPasswordField = constraintAnnotation.confirmPasswordField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field password = value.getClass().getDeclaredField(passwordField);
            Field confirmPassword = value.getClass().getDeclaredField(confirmPasswordField);

            password.setAccessible(true);
            confirmPassword.setAccessible(true);

            Object passwordValue = password.get(value);
            Object confirmPasswordValue = confirmPassword.get(value);

            if (passwordValue == null || !passwordValue.equals(confirmPasswordValue)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
