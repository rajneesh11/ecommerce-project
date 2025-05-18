package com.ekart.eKartOrderService.service;

import com.ekart.eKartOrderService.dto.CartDto;
import com.ekart.eKartOrderService.model.Cart;
import com.ekart.eKartOrderService.model.Product;
import com.ekart.eKartOrderService.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<?> placeOrder(String email) {
        Optional<Cart> cartOptional = cartRepository.findByCartOwner(email);
        if (cartOptional.isEmpty())
            return null;
        Cart cart = cartOptional.get();
        return orderService.placeOrderViaCart(cart, email);
    }

    @Override
    public ResponseEntity<?> addToCart(Product product, String email) {
        return updateCartDetails(product, email, "ADD");
    }

    @Override
    public ResponseEntity<?> removeFromCart(Product product, String email) {
        return updateCartDetails(product, email, "DEL");
    }

    @Override
    public ResponseEntity<?> updateCart(Product product, String email) {
        return null;
    }

    private ResponseEntity<?> updateCartDetails(Product product, String email, String operation) {
        Optional<Cart> cartOptional = cartRepository.findByCartOwner(email);
        Cart cart;
        if (cartOptional.isEmpty()) {
            cart = new Cart();
            cart.setCartOwner(email);
        } else {
            cart = cartOptional.get();
        }

        switch (operation) {
            case "ADD":
                cart.addProduct(product);
                break;
            case "DEL":
                if (!cart.getProducts().isEmpty()) {
                    Set<Product> products = cart.getProducts();
                    for (Product p : products) {
                        if (p.getId().equals(product.getId())) {
                            products.remove(p);
                            break;
                        }
                    }
                    cart.setProducts(products);
                }
        }
        Cart save = cartRepository.save(cart);
        CartDto dto = new CartDto();
        dto.setCartOwner(save.getCartOwner());
        dto.setProducts(save.getProducts());
        dto.setCartTotalAmount(save.getCartTotalAmount());
        return ResponseEntity.ok(dto);
    }
}
