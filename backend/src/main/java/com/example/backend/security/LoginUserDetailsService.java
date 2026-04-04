package com.example.backend.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.infrastructure.enums.UserType;
import com.example.backend.modules.system.entity.SysUser;
import com.example.backend.modules.system.mapper.SysMenuMapper;
import com.example.backend.modules.system.mapper.SysRoleMapper;
import com.example.backend.modules.system.mapper.SysUserMapper;
import com.example.backend.modules.user.entity.MallUser;
import com.example.backend.modules.user.mapper.MallUserMapper;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final MallUserMapper mallUserMapper;

    public LoginUserDetailsService(
            SysUserMapper sysUserMapper,
            SysRoleMapper sysRoleMapper,
            SysMenuMapper sysMenuMapper,
            MallUserMapper mallUserMapper
    ) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.mallUserMapper = mallUserMapper;
    }

    public LoginUser loadById(UserType userType, Long userId) {
        return switch (userType) {
            case ADMIN -> buildAdmin(loadAdminEntityById(userId));
            case MEMBER -> buildMember(loadMemberEntityById(userId));
        };
    }

    public SysUser loadAdminEntityByUsername(String username) {
        SysUser sysUser = sysUserMapper.selectOne(
                Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username).last("limit 1")
        );
        if (sysUser == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "账号或密码错误");
        }
        return sysUser;
    }

    public MallUser loadMemberEntityByUsername(String username) {
        MallUser mallUser = mallUserMapper.selectOne(
                Wrappers.<MallUser>lambdaQuery().eq(MallUser::getUsername, username).last("limit 1")
        );
        if (mallUser == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "账号或密码错误");
        }
        return mallUser;
    }

    public LoginUser buildAdmin(SysUser sysUser) {
        Set<String> roleCodes = new LinkedHashSet<>(sysRoleMapper.selectRoleCodesByUserId(sysUser.getId()));
        Set<String> permissionCodes = new LinkedHashSet<>(sysMenuMapper.selectPermissionCodesByUserId(sysUser.getId()));
        return LoginUser.builder()
                .userId(sysUser.getId())
                .username(sysUser.getUsername())
                .password(sysUser.getPassword())
                .displayName(sysUser.getRealName())
                .userType(UserType.ADMIN)
                .enabled(sysUser.getStatus() != null && sysUser.getStatus() == 1)
                .roleCodes(roleCodes)
                .permissionCodes(permissionCodes)
                .build();
    }

    public LoginUser buildMember(MallUser mallUser) {
        return LoginUser.builder()
                .userId(mallUser.getId())
                .username(mallUser.getUsername())
                .password(mallUser.getPassword())
                .displayName(mallUser.getNickname())
                .userType(UserType.MEMBER)
                .enabled(mallUser.getStatus() != null && mallUser.getStatus() == 1)
                .roleCodes(new LinkedHashSet<>(List.of("MEMBER")))
                .permissionCodes(Set.of("mall:profile:view"))
                .build();
    }

    private SysUser loadAdminEntityById(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, ResultCode.UNAUTHORIZED.getMessage());
        }
        return sysUser;
    }

    private MallUser loadMemberEntityById(Long userId) {
        MallUser mallUser = mallUserMapper.selectById(userId);
        if (mallUser == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, ResultCode.UNAUTHORIZED.getMessage());
        }
        return mallUser;
    }
}
