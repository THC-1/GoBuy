package com.example.backend.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank(message = "用户名不能为空")
        @Size(min = 4, max = 20, message = "用户名长度需在4到20位之间")
        String username,
        @Size(max = 20, message = "昵称长度不能超过20位")
        String nickname,
        @NotBlank(message = "密码不能为空")
        @Size(min = 6, max = 20, message = "密码长度需在6到20位之间")
        String password,
        String phone,
        @Email(message = "邮箱格式不正确")
        String email
) {
}
