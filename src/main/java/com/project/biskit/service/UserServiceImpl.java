package com.project.biskit.service;

import com.project.biskit.entity.User;
import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.model.AuthenticationRequest;
import com.project.biskit.model.AuthenticationResponse;
import com.project.biskit.model.SignUpRequest;
import com.project.biskit.repository.UserRepository;
import com.project.biskit.security.CustomUserDetailService;
import com.project.biskit.security.JwtUtil;
import com.project.biskit.utils.ResponseMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailService userDetailService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> authenticateUser(AuthenticationRequest request) throws BadRequestException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadRequestException(ResponseMessages.INVALID_LOGIN_REQUEST);
        }

        final String jwt = generateJwt(request.getEmail());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @Override
    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) throws BadRequestException, ConflictException {

        if (Objects.isNull(signUpRequest.getEmail()) || signUpRequest.getEmail().isEmpty() ||
                Objects.isNull(signUpRequest.getName()) || signUpRequest.getName().isEmpty() ||
                Objects.isNull(signUpRequest.getPassword()) || signUpRequest.getPassword().isEmpty())
            throw new BadRequestException(ResponseMessages.INVALID_SIGN_UP_REQUEST);

        User user = new User();
        BeanUtils.copyProperties(signUpRequest, user);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ConflictException(ResponseMessages.ACCOUNT_ALREADY_EXISTS);
        }

        final String jwt = generateJwt(signUpRequest.getEmail());

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.CREATED);
    }

    private String generateJwt(String email) {
        final UserDetails userDetails = userDetailService.loadUserByUsername(email);

        return jwtUtil.generateToken(userDetails);
    }
}
