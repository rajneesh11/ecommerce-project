package com.ekart.eKartOrderService.controller;

import com.ekart.eKartOrderService.model.Cart;
import com.ekart.eKartOrderService.model.Product;
import com.ekart.eKartOrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id, @RequestHeader String email) {
        return service.cancelOrder(id, email);
    }

    @PostMapping("/placeCartOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Cart cart, @RequestHeader String email) {
        return service.placeOrderViaCart(cart, email);
    }

    @PostMapping("/placeDirectOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Set<Product> products,
                                        @RequestHeader(value = "subject") String email) {
        return service.placeOrderViaDirect(products, email);
    }

    /*@PostMapping("/orderList")
    public ResponseEntity<?> orderList(@RequestHeader String email) {
        return null;
    }*/
    @PostMapping("/orderListForSeller")
    public ResponseEntity<?> orderListForSeller(@RequestHeader(value = "subject") String email) {
        return service.orderListForSeller(email);
    }
}
