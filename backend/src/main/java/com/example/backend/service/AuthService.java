package com.example.backend.service;

import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.LoginVO;
import com.example.backend.dto.response.UserVO;

public interface AuthService {

    LoginVO register(RegisterRequest request);

    LoginVO login(LoginRequest request);

    LoginVO refreshToken(String refreshToken);

    UserVO getCurrentUser(Long userId);

    void logout(Long userId);

    Long parseUserIdFromToken(String token);
}
