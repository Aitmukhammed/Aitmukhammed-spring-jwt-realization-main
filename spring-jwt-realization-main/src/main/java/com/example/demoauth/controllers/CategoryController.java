package com.example.demoauth.controllers;

import com.example.demoauth.DTO.CategoryDTO;
import com.example.demoauth.DTO.ProductDTO;
import com.example.demoauth.details.ProductDetails;
import com.example.demoauth.models.Category;
import com.example.demoauth.models.Product;
import com.example.demoauth.repository.CategoryRepository;
import com.example.demoauth.repository.ProductRepository;
import com.example.demoauth.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/categories") // Общий путь для всех запросов связанных с категориями
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Получить продукты по названию категории
    @GetMapping("/{categoryName}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<ProductDTO> products = categoryService.findProductsByCategoryName(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
            return ResponseEntity.ok(categoryDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = new Category(category.getName(), category.getCategoryCode());
        categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        try {
            Category category = categoryService.updateCategory(categoryId, updatedCategory);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
