package com.example.demoauth.service;

import com.example.demoauth.DTO.CategoryDTO;
import com.example.demoauth.DTO.ProductDTO;
import com.example.demoauth.details.ProductDetails;
import com.example.demoauth.models.Category;
import com.example.demoauth.models.Product;
import com.example.demoauth.repository.CategoryRepository;
import com.example.demoauth.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

//  mapToProductDTO - привязка к DTO Продукта

    public List<ProductDTO> findProductsByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setPictureUrl(product.getPictureUrl());
        return productDTO;
    }

    // Вытащить все виды категории
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapCategoryDetails)
                .collect(Collectors.toList());
    }
    private CategoryDTO mapCategoryDetails(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setId(category.getId());
//        categoryDTO.setName(category.getName());

        categoryDTO.setCategoryCode(category.getCategoryCode());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public void save(Category category) {
        log.info("category--: " + category);
        categoryRepository.save(category);
    }
}
