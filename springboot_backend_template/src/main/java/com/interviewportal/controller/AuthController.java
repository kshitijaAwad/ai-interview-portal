package com.interviewportal.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewportal.dto.LoginRequest;
import com.interviewportal.dto.LoginResponse;
import com.interviewportal.dto.RegisterRequest;
import com.interviewportal.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(userService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid
            @RequestBody LoginRequest request) {
    	

        return ResponseEntity.ok(userService.login(request));
    }
}