package com.ekart.eKartOrderService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "auth-service", url = "http://localhost:8081", path = "/auth-service")
public interface UserAddressServiceClient {
    @GetMapping("get")
    ResponseEntity<?> getUserPreferredAddress();

    @PostMapping("set")
    ResponseEntity<?> setUserPreferredAddress(@RequestParam UUID addressId);
}
