package com.ekart.eKartOrderService.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double orderAmount;
    //    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
    @ManyToMany
    private Set<Product> products;
    @CreationTimestamp
    private Date createdDate;
    private String orderStatus = OrderStatus.PENDING.name();
    //    @Column(unique = true,nullable = false)
    private String userEmail;
    private String address;
    private UUID paymentId;


    public void setProducts(Set<Product> productsToAdd) {
        products = productsToAdd;
        updateOrderAmount();
    }

    @PrePersist
    @PreUpdate
    private void prePersistOrUpdate() {
        updateOrderAmount();
    }

    private void updateOrderAmount() {
        this.orderAmount = products.stream()
                .mapToDouble(p ->
                        p.getPrice().doubleValue() * p.getAddedQuantity())
                .sum();
    }
}
