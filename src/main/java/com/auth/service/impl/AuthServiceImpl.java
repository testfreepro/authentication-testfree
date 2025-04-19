package com.auth.service.impl;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import com.auth.security.JwtUtil;
import com.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set custom fields from the request
        if (request.getCustomFields() != null) {
            user.setCustomFields(request.getCustomFields());
        }

        user = userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = UUID.randomUUID().toString();

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(jwtUtil.getJwtExpiration());
        response.setUser(convertToDto(user));

        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = UUID.randomUUID().toString();

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(jwtUtil.getJwtExpiration());
        response.setUser(convertToDto(user));

        return response;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // Implement refresh token logic
        return null;
    }

    @Override
    public void logout(String token) {
        // Implement logout logic
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        String resetToken = UUID.randomUUID().toString();
        user.setResetPasswordToken(resetToken);
        user.setResetPasswordExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        // Send email with reset token
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token).orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiry(null);
        userRepository.save(user);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setCustomFields(user.getCustomFields());
        dto.setEmailVerified(user.isEmailVerified());
        dto.setPhoneVerified(user.isPhoneVerified());
        return dto;
    }
} 