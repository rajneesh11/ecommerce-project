package com.ekart.eKartOrderService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "payment-service", url = "http://localhost:8084", path = "/payment-service")
public interface PaymentServiceClient {
    @PostMapping("pay")
    ResponseEntity<?> pay(@RequestBody double orderAmount);
    @PostMapping("updateOrderId")
    ResponseEntity<?> updateOrderId(@RequestParam UUID paymentId, @RequestParam UUID orderId);
}
