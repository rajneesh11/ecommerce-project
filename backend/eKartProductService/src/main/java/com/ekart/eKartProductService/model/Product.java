package com.ekart.eKartProductService.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Column(length = 2000)
    private String description;
    private BigDecimal price;
    private String category;
    private String brand;
    private int stockQuantity;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String imageUrl;
    private double weight;
    private String dimensions;
    private double rating;
    private UUID addedByUserId;
    private String addedBySellerEmail;
    private int addedQuantity = 1;
    private boolean published = false;
}
