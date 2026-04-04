package com.example.backend.modules.auth.controller;

import com.example.backend.common.api.Result;
import com.example.backend.modules.auth.dto.AdminLoginRequest;
import com.example.backend.modules.auth.dto.UserLoginRequest;
import com.example.backend.modules.auth.dto.UserRegisterRequest;
import com.example.backend.modules.auth.service.AuthService;
import com.example.backend.modules.auth.vo.AuthUserInfoVO;
import com.example.backend.modules.auth.vo.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/admin/login")
    public Result<LoginResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        return Result.success(authService.adminLogin(request));
    }

    @GetMapping("/api/admin/me")
    @PreAuthorize("hasAuthority('dashboard:view')")
    public Result<AuthUserInfoVO> currentAdminProfile() {
        return Result.success(authService.getCurrentAdminProfile());
    }

    @PostMapping("/api/user/register")
    public Result<LoginResponse> userRegister(@Valid @RequestBody UserRegisterRequest request) {
        return Result.success(authService.userRegister(request));
    }

    @PostMapping("/api/user/login")
    public Result<LoginResponse> userLogin(@Valid @RequestBody UserLoginRequest request) {
        return Result.success(authService.userLogin(request));
    }

    @GetMapping("/api/user/me")
    @PreAuthorize("hasAuthority('mall:profile:view')")
    public Result<AuthUserInfoVO> currentUserProfile() {
        return Result.success(authService.getCurrentUserProfile());
    }
}
