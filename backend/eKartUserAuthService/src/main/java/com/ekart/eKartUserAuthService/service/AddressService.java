package com.ekart.eKartUserAuthService.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AddressService {
    ResponseEntity<?> getPreferredAddress();

    ResponseEntity<?> setPreferredAddress(UUID addressId);
}
