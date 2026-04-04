package com.example.backend.modules.auth.vo;

import java.util.Set;

public record AuthUserInfoVO(
        Long userId,
        String username,
        String displayName,
        String userType,
        Set<String> roleCodes,
        Set<String> permissionCodes
) {
}
