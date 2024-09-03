package com.example.moscowcommerce_backend.Auth.Infrastructure.Security;

import com.example.moscowcommerce_backend.Auth.Domain.ITokenManagement;
import com.example.moscowcommerce_backend.Users.Domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Component
public class TokenManagement implements ITokenManagement {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private Long expirationTime;

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setClaims(Collections.emptyMap())
                .setSubject(user.getEmail())
                .setIssuedAt( new Date(System.currentTimeMillis()) )
                .setExpiration( new Date(System.currentTimeMillis() + expirationTime) )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, String email) {
        String _tokenEmail = extractEmailFromToken(token);
        return (_tokenEmail.equals(email) && !isTokenExpired(token));
    }

    @Override
    public String extractEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
