package com.investment_qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.investment_qna.DTO.LoginRequest;
import com.investment_qna.DTO.RegisterRequest;
import com.investment_qna.model.User;
import com.investment_qna.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
    
    public User findOrCreateGoogleUser(String email, String name) {
        return userRepository.findByEmail(email)
            .orElseGet(() -> {
                User user = new User();
                user.setEmail(email);
                user.setName(name);
//                user.setProvider("GOOGLE"-);
                return userRepository.save(user);
            });
    }

}
