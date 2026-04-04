package com.example.backend.modules.auth.service;

import com.example.backend.modules.auth.dto.AdminLoginRequest;
import com.example.backend.modules.auth.dto.UserLoginRequest;
import com.example.backend.modules.auth.dto.UserRegisterRequest;
import com.example.backend.modules.auth.vo.AuthUserInfoVO;
import com.example.backend.modules.auth.vo.LoginResponse;

public interface AuthService {

    LoginResponse adminLogin(AdminLoginRequest request);

    LoginResponse userLogin(UserLoginRequest request);

    LoginResponse userRegister(UserRegisterRequest request);

    AuthUserInfoVO getCurrentAdminProfile();

    AuthUserInfoVO getCurrentUserProfile();
}
