package com.mozip.handler.ex;

import java.util.HashMap;
import java.util.Map;

public class CustomValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap = new HashMap<>();

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
