package com.openrun.common.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
    private Map<String, String> fieldErrors;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = new HashMap<>();
    }

    public void addFieldError(String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new HashMap<>();
        }
        fieldErrors.put(field, message);
    }
}
