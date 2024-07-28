package com.example.demoauth.repository;

import com.example.demoauth.details.CartDetails;
import com.example.demoauth.models.Cart;
import com.example.demoauth.models.Product;
import com.example.demoauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndProduct(User user, Product product);

    List<Cart> findByProduct(Product product); // Add this method

    List<Cart> findByUser(User user);

    @Query("SELECT new com.example.demoauth.details.CartDetails(u.id, u.username, u.email, p.id, p.name, p.pictureUrl, p.price, c.quantity) " +
            "FROM Cart c JOIN c.user u JOIN c.product p WHERE u.id = :userId")
    List<CartDetails> findUserCartDetails(@Param("userId") Long userId);
}

