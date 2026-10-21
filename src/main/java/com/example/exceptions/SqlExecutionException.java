package com.example.exceptions;

public class SqlExecutionException extends FrameworkException {
    public SqlExecutionException(String message) {
        super(message);
    }

    public SqlExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
