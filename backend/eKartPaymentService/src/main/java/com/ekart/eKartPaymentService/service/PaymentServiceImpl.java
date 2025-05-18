package com.ekart.eKartPaymentService.service;

import com.ekart.eKartPaymentService.model.Payment;
import com.ekart.eKartPaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository repository;

    @Override
    public ResponseEntity<?> orderPayment(double orderAmount) {
        if (orderAmount == 0)
            return ResponseEntity.badRequest().build();
        Payment payment = new Payment();
        payment.setPaymentAmount(orderAmount);
        Payment save = repository.save(payment);
        return ResponseEntity.ok(save.getId());
    }

    @Override
    public ResponseEntity<?> orderIdUpdate(UUID paymentId, UUID orderId) {
        Optional<Payment> paymentOptional = repository.findById(paymentId);
        if (paymentOptional.isEmpty())
            return ResponseEntity.badRequest().build();
        Payment payment = paymentOptional.get();
        payment.setOrderId(orderId);
        repository.save(payment);
        return ResponseEntity.ok().build();
    }

}
