package com.nexbuy.notification.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    "nexbuy-secret-key-12345678901234567890"
                            .getBytes(StandardCharsets.UTF_8));

    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}