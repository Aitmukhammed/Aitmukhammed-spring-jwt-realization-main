package com.example.demoauth.service;

import com.example.demoauth.DTO.CategoryDTO;
import com.example.demoauth.DTO.ProductDTO;
import com.example.demoauth.details.ProductDetails;
import com.example.demoauth.models.Cart;
import com.example.demoauth.models.Category;
import com.example.demoauth.models.Product;
import com.example.demoauth.repository.CategoryRepository;
import com.example.demoauth.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setPictureUrl(product.getPictureUrl());
        Category category = categoryRepository.findByCategoryCode(product.getCategory().getCategoryCode())
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        productDTO.setCategory(category);
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
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryCode(category.getCategoryCode());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();

            List<Product> categoriesWithProduct = productRepository
                    .findByCategoryCategoryCode(category.getCategoryCode());

            Optional<Category> findCategory = categoryRepository.findByCategoryCode(1110L);
            Category getFindCategory = findCategory.get();
            log.info("getFindCategory : " + getFindCategory);
//          getFindCategory : Category(id=18, name=Не выбрано категория, categoryCode=1110, products=[com.example.demoauth.models.Product@eee3227])
            for (Product product : categoriesWithProduct) {
                product.setCategory(getFindCategory);
                productRepository.save(product);
            }
            categoryRepository.delete(category);
        } else {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
    }

    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            Long oldCategoryCode = existingCategory.getCategoryCode();

            // Найти временную категорию (например, "Uncategorized")
            Optional<Category> tempCategoryOpt = categoryRepository.findByCategoryCode(1113L);
            if (!tempCategoryOpt.isPresent()) {
                // Создать временную категорию
                Category tempCategory = new Category("Uncategorized", 1113L);
                categoryRepository.save(tempCategory);
            }
            Category tempCategory = tempCategoryOpt.get();

            // Переместить все продукты во временную категорию
            List<Product> products = productRepository.findByCategoryCategoryCode(oldCategoryCode);
            for (Product product : products) {
                product.setCategory(tempCategory);
                productRepository.save(product);
            }

            // Обновить категорию
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setCategoryCode(updatedCategory.getCategoryCode());
            categoryRepository.save(existingCategory);

            // Вернуть продукты в обновленную категорию
            for (Product product : products) {
                product.setCategory(existingCategory);
                productRepository.save(product);
            }

            return existingCategory;
        } else {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
    }

}

// updateCategory = сначала временно переводит продукты в другую категорию,
// затем обновляет основную категорию, и наконец, возвращает продукты в обновленную категорию

// Проблема:
// При обновлении categoryCode в категории, связанные продукты продолжают ссылаться
// на старое значение categoryCode. Это вызывает нарушение ограничения внешнего ключа, так как
// категория с этим categoryCode уже не существует.

// Решение:
// Временно перевести продукты в другую категорию.
// Обновить основную категорию с новым categoryCode.
// Вернуть продукты в обновленную категорию с новым categoryCode.
