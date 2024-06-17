package com.ekart.eKartUserAuthService.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table
public class PostalAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID addressId;
//    private String userId;
    private String pinCode;
    private String street;
    private String city;
    private String state;
    private String country;
}
