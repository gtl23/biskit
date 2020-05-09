package com.project.biskit.exceptions;

public class ConflictException extends Exception {

    final String errorMsg;

    public ConflictException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
