package com.example.backend.security;

import com.example.backend.config.JwtProperties;
import com.example.backend.infrastructure.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(LoginUser loginUser) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(resolveExpireMinutes(loginUser.getUserType()), ChronoUnit.MINUTES);
        return Jwts.builder()
                .subject(loginUser.getUsername())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .claim("uid", loginUser.getUserId())
                .claim("ut", loginUser.getUserType().name())
                .signWith(secretKey)
                .compact();
    }

    public TokenPayload parseToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long userId = claims.get("uid", Number.class).longValue();
        UserType userType = UserType.valueOf(claims.get("ut", String.class));
        return new TokenPayload(userId, claims.getSubject(), userType);
    }

    private long resolveExpireMinutes(UserType userType) {
        return userType == UserType.ADMIN
                ? jwtProperties.getAdminExpireMinutes()
                : jwtProperties.getUserExpireMinutes();
    }

    public record TokenPayload(Long userId, String username, UserType userType) {
    }
}
