package com.ekart.eKartOrderService.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String cartOwner;
    private double cartTotalAmount;
    @ManyToMany
    private Set<Product> products = new HashSet<>();
//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
////    @JoinColumn(name = "order_id")
//    private Set<Order> orderSet = new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);
        updateOrderAmount();
    }

    @PrePersist
    @PreUpdate
    private void prePersistOrUpdate() {
        updateOrderAmount();
    }

    private void updateOrderAmount() {
        this.cartTotalAmount = products.stream()
                .mapToDouble(p ->
                        p.getPrice().doubleValue() * p.getAddedQuantity())
                .sum();
    }
}
