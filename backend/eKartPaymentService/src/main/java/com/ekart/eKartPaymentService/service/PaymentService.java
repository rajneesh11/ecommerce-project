package com.ekart.eKartPaymentService.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface PaymentService {
    ResponseEntity<?> orderPayment(double orderAmount);

    ResponseEntity<?> orderIdUpdate(UUID paymentId, UUID orderId);
}
