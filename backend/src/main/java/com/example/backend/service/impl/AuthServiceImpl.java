package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.common.ResultCode;
import com.example.backend.config.JwtConfig;
import com.example.backend.dto.request.LoginRequest;
import com.example.backend.dto.request.RegisterRequest;
import com.example.backend.dto.response.LoginVO;
import com.example.backend.dto.response.UserVO;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.AuthService;
import com.example.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    @Override
    public LoginVO register(RegisterRequest request) {
        checkUsernameExists(request.getUsername());
        checkEmailExists(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setNickname(request.getUsername());
        user.setStatus(1);
        userMapper.insert(user);

        log.info("用户注册成功: username={}", request.getUsername());

        return buildLoginVO(user.getId());
    }

    @Override
    public LoginVO login(LoginRequest request) {
        User user = findUserByAccount(request.getAccount());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "密码错误");
        }

        if (user.getStatus() != 1) {
            throw new BusinessException(ResultCode.FORBIDDEN, "账号已被禁用");
        }

        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);

        log.info("用户登录成功: username={}", user.getUsername());

        return buildLoginVO(user.getId());
    }

    @Override
    public LoginVO refreshToken(String refreshToken) {
        Long userId = jwtUtil.parseToken(refreshToken);

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "用户不存在");
        }
        if (user.getStatus() != 1) {
            throw new BusinessException(ResultCode.FORBIDDEN, "账号已被禁用");
        }

        log.info("刷新Token成功: userId={}", userId);

        return buildLoginVO(userId);
    }

    @Override
    public UserVO getCurrentUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }

        return convertToUserVO(user);
    }

    @Override
    public void logout(Long userId) {
        log.info("用户登出: userId={}", userId);
    }

    @Override
    public Long parseUserIdFromToken(String token) {
        return jwtUtil.parseToken(token);
    }

    private void checkUsernameExists(String username) {
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (count > 0) {
            throw new BusinessException(ResultCode.CONFLICT, "用户名已存在");
        }
    }

    private void checkEmailExists(String email) {
        Long count = userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (count > 0) {
            throw new BusinessException(ResultCode.CONFLICT, "邮箱已存在");
        }
    }

    private User findUserByAccount(String account) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, account).last("LIMIT 1"));
        if (user != null) {
            return user;
        }

        user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, account).last("LIMIT 1"));
        if (user == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "账号或密码错误");
        }

        return user;
    }

    private LoginVO buildLoginVO(Long userId) {
        String accessToken = jwtUtil.generateAccessToken(userId);
        String refreshToken = jwtUtil.generateRefreshToken(userId);
        UserVO userVO = getCurrentUser(userId);

        return new LoginVO(accessToken, refreshToken, jwtConfig.getAccessTokenExpiration() / 1000, userVO);
    }

    private UserVO convertToUserVO(User user) {
        return new UserVO(user.getId(), user.getUsername(), user.getEmail(), user.getNickname(), user.getAvatarUrl());
    }
}
