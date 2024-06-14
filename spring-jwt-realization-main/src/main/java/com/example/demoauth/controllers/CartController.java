package com.example.demoauth.controllers;

import com.example.demoauth.details.CartDetails;
import com.example.demoauth.models.Cart;
import com.example.demoauth.models.User;
import com.example.demoauth.service.CartService;
import com.example.demoauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartDetails>> getUserCartItems(@PathVariable Long userId) {
        List<CartDetails> cartDetailsList = cartService.getUserCartDetails(userId);
        return ResponseEntity.ok(cartDetailsList);
    }
}
