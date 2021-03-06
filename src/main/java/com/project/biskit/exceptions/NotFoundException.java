package com.project.biskit.exceptions;

public class NotFoundException extends Exception {

    final String errorMsg;

    public NotFoundException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
