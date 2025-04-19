package com.auth.service;

import com.auth.dto.AuthResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String refreshToken);

    void logout(String token);

    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);
} 