package com.ekart.eKartUserAuthService.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.function.Function;


public interface JwtService {
    String generateToken(UserDetails userDetails);
    SecretKey getSecretKey();
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimResolver);
    String getUsernameFromToken(String token);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
