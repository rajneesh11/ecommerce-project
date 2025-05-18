package com.ekart.eKartOrderService.service;

import com.ekart.eKartOrderService.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface CartService {
    ResponseEntity<?> placeOrder(String email);
    ResponseEntity<?> addToCart(Product product, String email);
    ResponseEntity<?> removeFromCart(Product product, String email);
    ResponseEntity<?> updateCart(Product product, String email);
}
