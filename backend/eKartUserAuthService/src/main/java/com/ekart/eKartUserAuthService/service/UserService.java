package com.ekart.eKartUserAuthService.service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getPreferredAddress(String email);
}
