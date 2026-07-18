package com.interviewportal.service;

import com.interviewportal.dto.LoginRequest;
import com.interviewportal.dto.LoginResponse;
import com.interviewportal.dto.RegisterRequest;

import jakarta.validation.Valid;

public interface UserService {

	String register(RegisterRequest request);

	LoginResponse login(@Valid LoginRequest request);
}
