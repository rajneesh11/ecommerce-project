package com.ekart.eKartProductService.repository;

import com.ekart.eKartProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(String category);

    List<Product> findByNameLike(String name);

    List<Product> findByPriceBetween(BigDecimal price, BigDecimal price2);

    List<Product> findByAddedBySellerEmail(String sellerEmail);

    Optional<Product> findByIdAndAddedBySellerEmail(UUID id, String addedBySellerEmail);
}
