package com.ekart.eKartOrderService.controller;

import com.ekart.eKartOrderService.model.Product;
import com.ekart.eKartOrderService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService service;

    @PostMapping("addToCart")
    public ResponseEntity<?> addToCart(@RequestBody Product product, @RequestParam String email) {
        return service.addToCart(product, email);
    }

    @PostMapping("updateCart")
    public ResponseEntity<?> updateCart(@RequestBody Product product, @RequestParam String email) {
        return service.updateCart(product, email);
    }

    @PostMapping("removeFromCart")
    public ResponseEntity<?> removeFromCart(@RequestBody Product product, @RequestParam String email) {
        return service.removeFromCart(product, email);
    }

    @GetMapping("placeOrder")
    public ResponseEntity<?> placeOrder(@RequestParam String email) {
        return service.placeOrder(email);
    }
}
