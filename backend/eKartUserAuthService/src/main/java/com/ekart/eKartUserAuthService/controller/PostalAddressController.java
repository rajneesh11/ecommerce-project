package com.ekart.eKartUserAuthService.controller;

import com.ekart.eKartUserAuthService.service.AddressService;
import com.ekart.eKartUserAuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/address/preferredAddress")
public class PostalAddressController {
    @Autowired
    private AddressService service;

    @GetMapping("get")
    ResponseEntity<?> getUserPreferredAddress() {
        return service.getPreferredAddress();
    }

    @PostMapping("set")
    ResponseEntity<?> setUserPreferredAddress(@RequestParam UUID addressId) {
        return service.setPreferredAddress(addressId);
    }
}
