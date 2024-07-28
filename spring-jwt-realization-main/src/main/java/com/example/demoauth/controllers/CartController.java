package com.example.demoauth.controllers;

import com.example.demoauth.details.CartDetails;
import com.example.demoauth.models.Cart;
import com.example.demoauth.models.User;
import com.example.demoauth.repository.CartRepository;
import com.example.demoauth.repository.UserRepository;
import com.example.demoauth.service.CartService;
import com.example.demoauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        cartService.deleteToCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartDetails>> getUserCartItems(@PathVariable Long userId) {
        List<CartDetails> cartDetailsList = cartService.getUserCartDetails(userId);
        return ResponseEntity.ok(cartDetailsList);
    }

//    @DeleteMapping("/delete")


    @GetMapping("/quantities")
    public ResponseEntity<Map<Long, Integer>> getCartQuantities(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Cart> cartItems = cartRepository.findByUser(user);
        Map<Long, Integer> quantities = cartItems.stream()
                .collect(Collectors.toMap(
                        cart -> cart.getProduct().getId(),
                        Cart::getQuantity,
                        Integer::sum
                ));
        return ResponseEntity.ok(quantities);
    }
}
