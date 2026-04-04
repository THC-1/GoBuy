package com.example.backend.modules.auth.vo;

public record LoginResponse(
        String token,
        String tokenType,
        AuthUserInfoVO userInfo
) {
}
