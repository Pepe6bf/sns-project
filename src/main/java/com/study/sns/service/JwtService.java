package com.study.sns.global.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenUtils {

    /**
     * JWT 생성 비즈니스 로직
     */
    public static String generateToken(
            String email,
            String key,
            Long expiredTimeMs
    ) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key), SignatureAlgorithm.HS512)
                .compact();
    }

    // Key 생성 로직
    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {


    }

    public static String getEmail(
            String token,
            String key
    ) {
        return extractClaims(token, key).get("email", String.class);
    }

    // 토큰 만료여부 검증
    public static boolean tokenIsExpired(
            String token,
            String key
    ) {
        Date expireDate = extractClaims(token, key).getExpiration();
        return expireDate.before(new Date());
    }

    // 토큰의 claims 정보를 가져옴
    private static Claims extractClaims(
            String token,
            String key
    ) {
        return Jwts.parserBuilder().setSigningKey(getKey(key))
                .build().parseClaimsJws(token).getBody();
    }
}
