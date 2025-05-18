package com.ekart.eKartOrderService.model;

public enum OrderStatus {
    PENDING,
    CANCELED,
    IN_TRANSIT,
    DELIVERED,
    PAYMENT_AWAITING,
    PAYMENT_FAILED,
    USER_CANCELED,
    ORDER_PLACED
}
