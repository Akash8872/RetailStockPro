package com.retailstockpro.inventory.service;

import com.retailstockpro.inventory.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<Product> getLowStockProducts();

    List<Product> searchProducts(String keyword);

    long getTotalProductCount();

    long getLowStockCount();

    java.math.BigDecimal getTotalInventoryValue();
}
