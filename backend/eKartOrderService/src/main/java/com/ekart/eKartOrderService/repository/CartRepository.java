package com.ekart.eKartOrderService.repository;

import com.ekart.eKartOrderService.model.Cart;
import com.ekart.eKartOrderService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @NonNull
    Optional<Cart> findByCartOwner(String cartOwner);
}
