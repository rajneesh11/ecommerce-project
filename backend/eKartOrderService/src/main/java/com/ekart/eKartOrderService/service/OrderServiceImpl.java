package com.ekart.eKartOrderService.service;

import com.ekart.eKartOrderService.dto.OrderDto;
import com.ekart.eKartOrderService.feign.PaymentServiceClient;
import com.ekart.eKartOrderService.feign.UserAddressServiceClient;
import com.ekart.eKartOrderService.model.Cart;
import com.ekart.eKartOrderService.model.Orders;
import com.ekart.eKartOrderService.model.OrderStatus;
import com.ekart.eKartOrderService.model.Product;
import com.ekart.eKartOrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private UserAddressServiceClient userAddressServiceClient;
    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Override
    public ResponseEntity<?> placeOrderViaCart(Cart cart, String email) {
        if (cart == null || email.isEmpty())
            return ResponseEntity.badRequest().build();
        return placeOrderHelper(email, cart.getProducts());
    }

    @Override
    public ResponseEntity<?> placeOrderViaDirect(Set<Product> products, String email) {
        return placeOrderHelper(email, products);
    }

    @Override
    public ResponseEntity<?> cancelOrder(UUID orderId, String email) {
        Optional<Orders> orderOpt = repository.findById(orderId);
        if (orderOpt.isEmpty())
            return ResponseEntity.badRequest().build();
        Orders order = orderOpt.get();
        order.setOrderStatus(OrderStatus.CANCELED.name());
        return ResponseEntity.ok(repository.save(order));
    }

    @Override
    public ResponseEntity<?> orderList(String email) {
//        repository.findByProductsByAddedEmail(email);
        return null;
    }

    @Override
    public ResponseEntity<?> orderListForSeller(String email) {
        return ResponseEntity.ok(repository.findByProductsBySellerEmail(email));
    }

    private ResponseEntity<?> placeOrderHelper(String email, Set<Product> products) {
        Orders order = new Orders();
        order.setUserEmail(email);
        ResponseEntity<?> userPreferredAddress = userAddressServiceClient.getUserPreferredAddress();
        if (userPreferredAddress.getStatusCode() == HttpStatus.BAD_REQUEST)
            return ResponseEntity.badRequest().build();
        Object ob = userPreferredAddress.getBody();
        order.setAddress(ob.toString());
        order.setProducts(products);
        ResponseEntity<?> pay = paymentServiceClient.pay(order.getOrderAmount());
        if (pay.getStatusCode() == HttpStatus.BAD_REQUEST)
            return ResponseEntity.badRequest().build();
        UUID paymentId = (UUID) pay.getBody();
        order.setPaymentId(paymentId);
        if (paymentId != null) {
            order.setOrderStatus(OrderStatus.ORDER_PLACED.name());
        } else {
            order.setOrderStatus(OrderStatus.PAYMENT_FAILED.name());
        }
        Orders placedOrder = repository.save(order);
        paymentServiceClient.updateOrderId(paymentId, placedOrder.getId());
        // call notification service about order status
        OrderDto orderDto = new OrderDto(placedOrder.getId(), placedOrder.getOrderAmount(), placedOrder.getAddress());
        return ResponseEntity.ok(orderDto);
    }
}
