package com.ekart.eKartPaymentService.controller;

import com.ekart.eKartPaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @PostMapping("pay")
    public ResponseEntity<?> pay(@RequestParam double orderAmount) {
        return service.orderPayment(orderAmount);
    }

    @PostMapping("updateOrderId")
    public ResponseEntity<?> updateOrderId(@RequestBody UUID paymentId, @RequestBody UUID orderId) {
        return service.orderIdUpdate(paymentId, orderId);
    }

}
