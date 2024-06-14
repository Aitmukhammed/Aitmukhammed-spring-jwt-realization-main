package com.example.demoauth.service;

import com.example.demoauth.DTO.ProductDTO;
import com.example.demoauth.models.Product;
import com.example.demoauth.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private ProductRepository productRepository;

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
}
