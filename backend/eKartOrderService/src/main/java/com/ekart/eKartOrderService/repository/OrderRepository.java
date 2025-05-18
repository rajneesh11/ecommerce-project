package com.ekart.eKartOrderService.repository;

import com.ekart.eKartOrderService.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT DISTINCT o FROM Orders o JOIN FETCH o.products p WHERE p.addedBySellerEmail = :email")
    List<Orders> findByProductsBySellerEmail(String email);
}