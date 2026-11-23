package com.universales.practica2.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

	private final SecretKey secretKey;
	private final long expirationTime;
	
	public JwtService(
		
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.expiration-ms}") long expirationTime
			
	) {
		
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationTime = expirationTime;
		
	}
	
	public String generarToken(String username) {
		
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationTime);
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(now)
				.expiration(expiration)
				.signWith(secretKey)
				.compact();
		
	}
	
	public boolean isTokenValid(String token) {
		
		try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
		
	}
	
	private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
	
}
