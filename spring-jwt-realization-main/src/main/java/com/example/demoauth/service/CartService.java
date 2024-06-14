package com.example.demoauth.service;


import com.example.demoauth.details.CartDetails;
import com.example.demoauth.models.Cart;
import com.example.demoauth.models.Product;
import com.example.demoauth.models.User;
import com.example.demoauth.repository.CartRepository;
import com.example.demoauth.repository.ProductRepository;
import com.example.demoauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public void addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));

        Optional<Cart> optionalCart = cartRepository.findByUserAndProduct(user, product);
        Cart cart = optionalCart.orElse(new Cart());

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(cart.getQuantity() + quantity);
        cartRepository.save(cart);
    }
    public List<CartDetails> getUserCartDetails(Long userId) {
        return cartRepository.findUserCartDetails(userId);
    }
}
