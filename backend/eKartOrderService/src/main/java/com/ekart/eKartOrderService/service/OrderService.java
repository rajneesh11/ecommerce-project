package com.ekart.eKartOrderService.service;

import com.ekart.eKartOrderService.model.Cart;
import com.ekart.eKartOrderService.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.UUID;

public interface OrderService {
    ResponseEntity<?> placeOrderViaCart(Cart cart, String email);

    ResponseEntity<?> placeOrderViaDirect(Set<Product> products, String email);

    ResponseEntity<?> cancelOrder(UUID orderId, String email);

    ResponseEntity<?> orderList(String email);
    ResponseEntity<?> orderListForSeller(String email);

}
