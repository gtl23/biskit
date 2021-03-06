package com.project.biskit.exceptions;

import com.project.biskit.utils.ResponseMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException exception) {
        Map<String, String> response = new HashMap<>();
        response.put(ResponseMessages.RESPONSE_MSG_KEY, exception.getErrorMsg());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        Map<String, String> response = new HashMap<>();
        response.put(ResponseMessages.RESPONSE_MSG_KEY, exception.getErrorMsg());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> conflictException(ConflictException exception) {
        Map<String, String> response = new HashMap<>();
        response.put(ResponseMessages.RESPONSE_MSG_KEY, exception.getErrorMsg());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
