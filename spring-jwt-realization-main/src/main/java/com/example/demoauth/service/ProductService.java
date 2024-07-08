package com.example.demoauth.service;

import com.example.demoauth.details.ProductDetails;
import com.example.demoauth.models.Cart;
import com.example.demoauth.models.Product;
import com.example.demoauth.models.User;
import com.example.demoauth.pojo.AuthenticationFacade;
import com.example.demoauth.repository.CartRepository;
import com.example.demoauth.repository.ProductRepository;
import com.example.demoauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository; // если требуется

    @Autowired
    private AuthenticationFacade authenticationFacade; // если требуется

    @Autowired
    private CartRepository cartRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createProduct(Product product) {
        // Получаем текущего пользователя из контекста аутентификации
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        User currentUser = userRepository
                .findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Устанавливаем текущего пользователя для создаваемого продукта
        product.setUser(currentUser);
        productRepository.save(product);
    }

//    Метод isPresent() возвращает true, если значение присутствует.
//    Метод get() возвращает это значение, но вызывает NoSuchElementException,
//    если значение отсутствует.

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // Находим все корзины, содержащие удаляемый продукт
            List<Cart> cartsWithProduct = cartRepository.findByProduct(product);
            // Удаляем продукт из корзин
            for (Cart cart : cartsWithProduct) {
                cart.setProduct(null); // Удаление ссылки на продукт из корзины
                cartRepository.save(cart);
            }
            // Теперь можно безопасно удалить продукт
            productRepository.delete(product);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public List<ProductDetails> getAllProducts() {
        List<Product> products = productRepository.findAllOrderByCategoryNameDesc();
        return products.stream()
                .map(this::mapToProductDetails)
                .collect(Collectors.toList());
    }
    private ProductDetails mapToProductDetails(Product product) {
        if (product == null) {
            log.warn("Attempted to map null Product to ProductDetails");
            return null;
        }
        String username = (product.getUser() != null) ? product.getUser().getUsername() : "Unknown";
        return new ProductDetails(product.getId(), product.getName(), product.getPrice(), product.getPictureUrl(), username, product.getCategory());
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

// Нужно для функций = (getProductsByCategory) -> Подтягивает продуктов по id категории (Пока не надо = но функция работает)
//    public List<ProductDetails> getProductsByCategory(Long categoryId) {
//        List<Product> products = productRepository.findByCategoryId(categoryId);
//        return products.stream()
//                .map(this::mapToProductDetails)
//                .collect(Collectors.toList());
//    }
}
