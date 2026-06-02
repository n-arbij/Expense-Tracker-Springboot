package com.fintech.expense_planner.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.expense_planner.dto.UserDto.AuthResponse;
import com.fintech.expense_planner.dto.UserDto.LoginDto;
import com.fintech.expense_planner.dto.UserDto.RegisterDto;
import com.fintech.expense_planner.model.User;
import com.fintech.expense_planner.repository.UserRepository;
import com.fintech.expense_planner.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token, user.getUsername());
    }

    public AuthResponse login(LoginDto loginDto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
            )
        );

        String token = jwtUtil.generateToken(loginDto.getUsername());
        return new AuthResponse(token, loginDto.getUsername());
    }
    
}
