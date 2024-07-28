package com.example.demoauth.repository;

import com.example.demoauth.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryCode(Long categoryCode);

//    Category findByCategoryCode(Long categoryCode);

//    List<Category> findByCategory(Category category); // Add this method


//    @Query("SELECT c FROM Category c JOIN FETCH c.products p WHERE c.name = :categoryName")
//    List<Product> findProductsByCategoryName(String categoryName);
}
