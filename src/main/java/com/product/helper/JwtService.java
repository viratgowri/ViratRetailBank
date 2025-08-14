package com.product.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String appSecret = "ViratGowriIComeToTalkWithYouAgain"; // should be 32+ chars for HS256
    private final long expiresIn = 6048; // 7 days in milliseconds

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(appSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userEmail) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiresIn);

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims decodeToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            System.out.println("Token is not valid: " + e.getMessage());
            return null;
        }
    }

    public boolean isTokenIncluded(String authHeader) {
        return authHeader != null && authHeader.startsWith("Bearer ");
    }

    public String getAccessTokenFromHeader(String authHeader) {
        if (isTokenIncluded(authHeader)) {
            return authHeader.split(" ")[1];
        }
        return null;
    }
}
