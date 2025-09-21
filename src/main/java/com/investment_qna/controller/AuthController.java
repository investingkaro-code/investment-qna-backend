package com.investment_qna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.investment_qna.JwtUtil;
import com.investment_qna.DTO.AuthResponse;
import com.investment_qna.DTO.LoginRequest;
import com.investment_qna.DTO.RegisterRequest;
import com.investment_qna.model.User;
import com.investment_qna.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for user registration and login")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account and return JWT token")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(user, token));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        String token = jwtUtil.generateToken(user);
        System.out.println("Token = " + token);
        return ResponseEntity.ok(new AuthResponse(user, token));
    }
}

