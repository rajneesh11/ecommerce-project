package com.ekart.eKartUserAuthService.controller;

import com.ekart.eKartUserAuthService.dto.LoginRequest;
import com.ekart.eKartUserAuthService.dto.LoginResponse;
import com.ekart.eKartUserAuthService.dto.RegisterRequest;
import com.ekart.eKartUserAuthService.dto.RegisterResponse;
import com.ekart.eKartUserAuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
