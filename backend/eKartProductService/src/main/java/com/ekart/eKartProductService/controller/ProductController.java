package com.ekart.eKartProductService.controller;

import com.ekart.eKartProductService.model.Product;
import com.ekart.eKartProductService.model.ProductResponse;
import com.ekart.eKartProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("addProduct")
    ResponseEntity<ProductResponse> addProduct(@RequestBody Product product,
                                               @RequestHeader(value = "subject") String email) {
        return service.addProduct(product, email);
    }

    @PostMapping("updateProduct/{id}")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
                                                  @RequestBody Product product,
                                                  @RequestHeader(value = "subject") String email) {
        return service.updateProduct(id, product, email);
    }

    @DeleteMapping("deleteProduct/{id}")
    ResponseEntity<ProductResponse> deleteProduct(@PathVariable UUID id,
                                                  @RequestHeader(value = "subject") String email) {
        return service.deleteProduct(id, email);
    }

    @GetMapping("getProduct/{id}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id) {
        return service.getProduct(id);
    }

    @GetMapping("getProductList")
    ResponseEntity<ProductResponse> getProductList() {
        return service.getProductList();
    }
}
