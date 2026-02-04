package com.openrun.common.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Integer id) {
        super("Category not found with id: " + id);
    }
}
