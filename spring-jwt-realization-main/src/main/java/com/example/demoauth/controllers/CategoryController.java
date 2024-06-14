package com.example.demoauth.controllers;

import com.example.demoauth.DTO.ProductDTO;
import com.example.demoauth.models.Product;
import com.example.demoauth.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories") // Общий путь для всех запросов связанных с категориями
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Получить продукты по названию категории
    @GetMapping("/{categoryName}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryName(@PathVariable String categoryName) {
        List<ProductDTO> products = categoryService.findProductsByCategoryName(categoryName);
        return ResponseEntity.ok(products);
    }
}

//    @GetMapping("/{categoryId}") // Изменен путь для получения категории по ID
//    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
//        Category category = categoryService.findById(categoryId);
//        log.info("cat_id: " + categoryService.findById(categoryId));
//        return ResponseEntity.ok(category);
//    }
//
//    @GetMapping("/all") // Изменен путь для получения всех категорий
//    public ResponseEntity<List<Category>> getAllCategories() {
//        List<Category> categories = categoryService.findAll();
//        return ResponseEntity.ok(categories);
//    }
//
//    @PostMapping("/add") // Обновленный путь для добавления категории
//    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
//        Category newCategory = categoryService.save(category);
//        return ResponseEntity.ok(newCategory);
//    }
//
//    @DeleteMapping("/delete/{id}") // Изменен путь для удаления категории по ID
//    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
//        categoryService.delete(id);
//        return ResponseEntity.ok().build();
//    }