package com.example.backend.controller;

import com.example.backend.common.Result;
import com.example.backend.dto.response.UserVO;
import com.example.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public Result<UserVO> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        Long userId = authService.parseUserIdFromToken(token);
        UserVO userVO = authService.getCurrentUser(userId);
        return Result.success(userVO);
    }
}
