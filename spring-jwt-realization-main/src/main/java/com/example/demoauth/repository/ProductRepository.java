package com.example.demoauth.repository;

import com.example.demoauth.models.Product;
import com.example.demoauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser(User user);

    Optional<Product> findById(Long id);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryName(String categoryName);
}
