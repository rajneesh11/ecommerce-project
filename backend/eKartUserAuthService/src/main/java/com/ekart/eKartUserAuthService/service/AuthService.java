package com.ekart.eKartUserAuthService.service;

import com.ekart.eKartUserAuthService.dto.LoginResponse;
import com.ekart.eKartUserAuthService.dto.LoginRequest;
import com.ekart.eKartUserAuthService.dto.RegisterRequest;
import com.ekart.eKartUserAuthService.dto.RegisterResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<LoginResponse> login(LoginRequest request);
    ResponseEntity<RegisterResponse> register(RegisterRequest request);
}
