package com.example.backend.security;

import com.example.backend.infrastructure.enums.UserType;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
public class LoginUser implements UserDetails {

    private final Long userId;
    private final String username;
    private final String password;
    private final String displayName;
    private final UserType userType;
    private final boolean enabled;
    private final Set<String> roleCodes;
    private final Set<String> permissionCodes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> authorities = new LinkedHashSet<>();
        if (roleCodes != null) {
            roleCodes.stream()
                    .map(roleCode -> "ROLE_" + roleCode)
                    .forEach(authorities::add);
        }
        if (permissionCodes != null) {
            authorities.addAll(permissionCodes);
        }
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
