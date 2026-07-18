package com.interviewportal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interviewportal.dto.LoginRequest;
import com.interviewportal.dto.LoginResponse;
import com.interviewportal.dto.RegisterRequest;
import com.interviewportal.entity.Role;
import com.interviewportal.entity.User;
import com.interviewportal.repo.UserRepository;
import com.interviewportal.security.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEnoder;
	private final JwtUtil jwtUtil;
	@Override
	public String register(RegisterRequest request) {
		
		if(userRepo.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email Already Exists!!!");
		}
		
		User user = User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEnoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		userRepo.save(user);
		
		return "User registered Successfully!!!";
	}
	
	
	
	@Override
	public LoginResponse login(@Valid LoginRequest request) {
		User user = userRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid Email"));
		if(!passwordEnoder.matches(request.getPassword(),user.getPassword())) {
			throw new RuntimeException("Invalid Password !!!!");
		}
		String token = jwtUtil.generateToken(user.getEmail());
		return new LoginResponse(token);
	}

}
