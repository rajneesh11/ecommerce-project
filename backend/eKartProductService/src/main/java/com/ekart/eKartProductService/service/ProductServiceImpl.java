package com.ekart.eKartProductService.service;

import com.ekart.eKartProductService.model.ProductResponse;
import com.ekart.eKartProductService.model.Product;
import com.ekart.eKartProductService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public ResponseEntity<ProductResponse> addProduct(Product product, String email) {
        if (product == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ProductResponse(
                            "Invalid Product data", HttpStatus.BAD_REQUEST.value()));
        product.setAddedBySellerEmail(email);
        repository.save(product);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse(
                        "Product added successfully", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(UUID id, Product product, String email) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ProductResponse("Invalid Product ID", HttpStatus.BAD_REQUEST.value()));
        Product p = optional.get();
        if (!p.getAddedBySellerEmail().equals(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ProductResponse("You cannot update this product",
                            HttpStatus.BAD_REQUEST.value()));
        if (product.getPrice() != null)
            p.setPrice(product.getPrice());
        if (product.getStockQuantity() != 0)
            p.setStockQuantity(product.getStockQuantity());
        if (product.getRating() != 0)
            p.setRating(product.getRating());
        if (product.isPublished())
            p.setPublished(true);
        repository.save(p);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse("Product updated successfully", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ProductResponse> deleteProduct(UUID id, String email) {
        Optional<Product> optional = repository.findByIdAndAddedBySellerEmail(id, email);
        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductResponse("No Product with such ID and created by you", HttpStatus.NOT_FOUND.value()));
        if (!optional.get().getAddedBySellerEmail().equals(email))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ProductResponse("You cannot delete this product", HttpStatus.BAD_REQUEST.value()));

        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse("Product deleted successfully", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ProductResponse> getProduct(UUID id) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductResponse(
                            "Invalid Product ID", HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse(optional.get(),
                        "Product fetched successfully", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ProductResponse> getProductList(String email) {
        List<Product> productList = repository.findByAddedBySellerEmail(email);
        return getProductResponseResponseEntity(productList);
    }

    @Override
    public ResponseEntity<ProductResponse> searchProductByName(String name) {
        List<Product> productList = repository.findByNameLike(name);
        return getProductResponseResponseEntity(productList);
    }

    @Override
    public ResponseEntity<ProductResponse> searchProductByCategory(String category) {
        List<Product> productList = repository.findByCategory(category);
        return getProductResponseResponseEntity(productList);
    }

    @Override
    public ResponseEntity<ProductResponse> searchProductByPriceRange(double low, double high) {
        List<Product> productList = repository.findByPriceBetween(BigDecimal.valueOf(low),
                BigDecimal.valueOf(high));
        return getProductResponseResponseEntity(productList);
    }

    private ResponseEntity<ProductResponse> getProductResponseResponseEntity(List<Product> productList) {
        if (productList.isEmpty())
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ProductResponse("No Product found",
                            HttpStatus.OK.value()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProductResponse(productList,
                        "Products fetched successfully",
                        HttpStatus.OK.value()));
    }
}
