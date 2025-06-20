package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; 

import org.springframework.stereotype.Component;

import java.security.Key; 
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

  
    private final String SECRET = "my-super-secret-key-that-is-very-long-32-bytes!"; 

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

   
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(SECRET_KEY)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email,String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) 
                .compact();
    }

    public Boolean validateToken(String token, String email) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(email) && !isTokenExpired(token));
    }
}
