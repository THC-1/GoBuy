package com.example.backend.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.common.util.SecurityUtils;
import com.example.backend.modules.auth.dto.AdminLoginRequest;
import com.example.backend.modules.auth.dto.UserLoginRequest;
import com.example.backend.modules.auth.dto.UserRegisterRequest;
import com.example.backend.modules.auth.service.AuthService;
import com.example.backend.modules.auth.vo.AuthUserInfoVO;
import com.example.backend.modules.auth.vo.LoginResponse;
import com.example.backend.modules.system.entity.SysUser;
import com.example.backend.modules.system.mapper.SysUserMapper;
import com.example.backend.modules.user.entity.MallUser;
import com.example.backend.modules.user.mapper.MallUserMapper;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.security.LoginUser;
import com.example.backend.security.LoginUserDetailsService;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final LoginUserDetailsService loginUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MallUserMapper mallUserMapper;
    private final SysUserMapper sysUserMapper;

    public AuthServiceImpl(
            LoginUserDetailsService loginUserDetailsService,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            MallUserMapper mallUserMapper,
            SysUserMapper sysUserMapper
    ) {
        this.loginUserDetailsService = loginUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.mallUserMapper = mallUserMapper;
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    @Transactional
    public LoginResponse adminLogin(AdminLoginRequest request) {
        SysUser sysUser = loginUserDetailsService.loadAdminEntityByUsername(request.username());
        verifyEnabled(sysUser.getStatus());
        verifyAndUpgradeSystemPassword(sysUser, request.password());
        LoginUser loginUser = loginUserDetailsService.buildAdmin(sysUser);
        return buildLoginResponse(loginUser);
    }

    @Override
    @Transactional
    public LoginResponse userLogin(UserLoginRequest request) {
        MallUser mallUser = loginUserDetailsService.loadMemberEntityByUsername(request.username());
        verifyEnabled(mallUser.getStatus());
        verifyAndUpgradeMallPassword(mallUser, request.password());
        LoginUser loginUser = loginUserDetailsService.buildMember(mallUser);
        return buildLoginResponse(loginUser);
    }

    @Override
    @Transactional
    public LoginResponse userRegister(UserRegisterRequest request) {
        boolean usernameExists = mallUserMapper.selectCount(
                Wrappers.<MallUser>lambdaQuery().eq(MallUser::getUsername, request.username())
        ) > 0;
        if (usernameExists) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "用户名已存在");
        }
        if (StringUtils.hasText(request.phone())) {
            boolean phoneExists = mallUserMapper.selectCount(
                    Wrappers.<MallUser>lambdaQuery().eq(MallUser::getPhone, request.phone())
            ) > 0;
            if (phoneExists) {
                throw new BusinessException(ResultCode.BUSINESS_ERROR, "手机号已被注册");
            }
        }
        if (StringUtils.hasText(request.email())) {
            boolean emailExists = mallUserMapper.selectCount(
                    Wrappers.<MallUser>lambdaQuery().eq(MallUser::getEmail, request.email())
            ) > 0;
            if (emailExists) {
                throw new BusinessException(ResultCode.BUSINESS_ERROR, "邮箱已被注册");
            }
        }
        MallUser mallUser = new MallUser();
        mallUser.setUsername(request.username());
        mallUser.setNickname(StringUtils.hasText(request.nickname()) ? request.nickname() : request.username());
        mallUser.setPhone(request.phone());
        mallUser.setEmail(request.email());
        mallUser.setGender("secret");
        mallUser.setBio("");
        mallUser.setPassword(passwordEncoder.encode(request.password()));
        mallUser.setStatus(1);
        mallUser.setCreatedAt(LocalDateTime.now());
        mallUser.setUpdatedAt(LocalDateTime.now());
        mallUserMapper.insert(mallUser);
        LoginUser loginUser = loginUserDetailsService.buildMember(mallUser);
        return buildLoginResponse(loginUser);
    }

    @Override
    public AuthUserInfoVO getCurrentAdminProfile() {
        return toUserInfo(SecurityUtils.getLoginUser());
    }

    @Override
    public AuthUserInfoVO getCurrentUserProfile() {
        return toUserInfo(SecurityUtils.getLoginUser());
    }

    private void verifyEnabled(Integer status) {
        if (status == null || status != 1) {
            throw new BusinessException(ResultCode.FORBIDDEN, "账号已被禁用");
        }
    }

    private void verifyAndUpgradeSystemPassword(SysUser sysUser, String rawPassword) {
        if (passwordMatches(rawPassword, sysUser.getPassword())) {
            upgradeSystemPasswordIfNeeded(sysUser, rawPassword);
            return;
        }
        throw new BusinessException(ResultCode.UNAUTHORIZED, "账号或密码错误");
    }

    private void verifyAndUpgradeMallPassword(MallUser mallUser, String rawPassword) {
        if (passwordMatches(rawPassword, mallUser.getPassword())) {
            upgradeMallPasswordIfNeeded(mallUser, rawPassword);
            return;
        }
        throw new BusinessException(ResultCode.UNAUTHORIZED, "账号或密码错误");
    }

    private boolean passwordMatches(String rawPassword, String storedPassword) {
        if (storedPassword == null) {
            return false;
        }
        return isEncodedPassword(storedPassword)
                ? passwordEncoder.matches(rawPassword, storedPassword)
                : rawPassword.equals(storedPassword);
    }

    private void upgradeSystemPasswordIfNeeded(SysUser sysUser, String rawPassword) {
        if (!isEncodedPassword(sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(rawPassword));
            sysUser.setUpdatedAt(LocalDateTime.now());
            sysUserMapper.updateById(sysUser);
        }
    }

    private void upgradeMallPasswordIfNeeded(MallUser mallUser, String rawPassword) {
        if (!isEncodedPassword(mallUser.getPassword())) {
            mallUser.setPassword(passwordEncoder.encode(rawPassword));
            mallUser.setUpdatedAt(LocalDateTime.now());
            mallUserMapper.updateById(mallUser);
        }
    }

    private boolean isEncodedPassword(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }

    private LoginResponse buildLoginResponse(LoginUser loginUser) {
        return new LoginResponse(jwtTokenProvider.createToken(loginUser), "Bearer", toUserInfo(loginUser));
    }

    private AuthUserInfoVO toUserInfo(LoginUser loginUser) {
        return new AuthUserInfoVO(
                loginUser.getUserId(),
                loginUser.getUsername(),
                loginUser.getDisplayName(),
                loginUser.getUserType().name(),
                new LinkedHashSet<>(loginUser.getRoleCodes()),
                new LinkedHashSet<>(loginUser.getPermissionCodes())
        );
    }
}
