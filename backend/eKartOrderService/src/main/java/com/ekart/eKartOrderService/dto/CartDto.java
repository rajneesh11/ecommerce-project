package com.ekart.eKartOrderService.dto;

import com.ekart.eKartOrderService.model.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Data
public class CartDto {
//    private UUID id;
    private String cartOwner;
    private double cartTotalAmount;
    private Set<Product> products;
}
