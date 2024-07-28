package com.example.demoauth.repository;

import com.example.demoauth.models.Category;
import com.example.demoauth.models.Product;
import com.example.demoauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser(User user);

    Optional<Product> findById(Long id);

//@Query("SELECT p FROM Product p JOIN p.category c ORDER BY c.name DESC")
    @Query("SELECT p FROM Product p LEFT JOIN p.category c ORDER BY c.name DESC")
    List<Product> findAllOrderByCategoryNameDesc();

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByCategoryCategoryCode(Long categoryCode);
    List<Product> findByNameContainingIgnoreCase(String name);


}
