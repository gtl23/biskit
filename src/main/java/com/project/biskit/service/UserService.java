package com.project.biskit.service;

import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.model.AuthenticationRequest;
import com.project.biskit.model.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signUp(SignUpRequest signUpRequest) throws BadRequestException, ConflictException;

    ResponseEntity<?> authenticateUser(AuthenticationRequest request) throws BadRequestException;
}
