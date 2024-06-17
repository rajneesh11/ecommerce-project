package com.ekart.eKartUserAuthService.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Entity
@Table
public class EkartUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String email;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    )
    private Set<PostalAddress> postalAddress = new HashSet<>();
    private String fullName;
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    )
    private Set<Roles> roles = new HashSet<>();
    private String createdAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}
