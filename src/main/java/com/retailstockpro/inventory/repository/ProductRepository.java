package com.retailstockpro.inventory.repository;

import com.retailstockpro.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);

    // Find products where quantity is less than or equal to threshold
    @Query("SELECT p FROM Product p WHERE p.quantityInStock <= p.lowStockThreshold")
    List<Product> findLowStockProducts();

    List<Product> findByNameContainingIgnoreCase(String name);
}
