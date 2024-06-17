package com.ekart.eKartProductService.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Product product;
    private List<Product> productList;
    private String message;
    private int httpStatus;

    public ProductResponse(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ProductResponse(Product product, String message, int httpStatus) {
        this.product = product;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ProductResponse(List<Product> productList, String message, int httpStatus) {
        this.productList = productList;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ProductResponse(Product product, List<Product> productList, String message, int httpStatus) {
        this.product = product;
        this.productList = productList;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
