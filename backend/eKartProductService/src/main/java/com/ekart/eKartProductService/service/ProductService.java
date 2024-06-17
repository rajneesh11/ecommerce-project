package com.ekart.eKartProductService.service;

import com.ekart.eKartProductService.model.ProductResponse;
import com.ekart.eKartProductService.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ProductService {
    ResponseEntity<ProductResponse> addProduct(Product product, String email);

    ResponseEntity<ProductResponse> updateProduct(UUID id, Product product, String email);

    ResponseEntity<ProductResponse> deleteProduct(UUID id, String email);

    ResponseEntity<ProductResponse> getProduct(UUID id);

    ResponseEntity<ProductResponse> getProductList();
}
