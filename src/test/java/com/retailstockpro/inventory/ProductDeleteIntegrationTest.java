package com.retailstockpro.inventory;

import com.retailstockpro.inventory.model.Product;
import com.retailstockpro.inventory.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ProductDeleteIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testDeleteProduct() {
        // 1. Create a dummy product
        Product product = new Product();
        product.setName("Test Product for Delete");
        product.setPrice(BigDecimal.valueOf(100.00));
        product.setQuantityInStock(10);
        product.setLowStockThreshold(5);

        // We'll skip setting category/supplier for now unless they are strictly
        // required @NotNull
        // Based on Product.java, category and supplier are @ManyToOne but not @NotNull
        // in the entity fields themselves (only JOIN columns)
        // Check Product.java: category and supplier are private fields, not marked
        // @NotNull.
        // However, let's look at the database schema creation or constraints.
        // Assuming they are nullable for this test or we might get a violation if they
        // are required.
        // Let's safe-guard by not setting them first, if it fails, we'll know.

        Product savedProduct = productService.saveProduct(product);
        Long id = savedProduct.getId();
        Assertions.assertNotNull(id);

        // 2. Verify it exists
        Optional<Product> fetchedProduct = productService.getProductById(id);
        Assertions.assertTrue(fetchedProduct.isPresent());

        // 3. Delete it
        productService.deleteProduct(id);

        // 4. Verify it's gone
        Optional<Product> deletedProduct = productService.getProductById(id);
        Assertions.assertFalse(deletedProduct.isPresent(), "Product should be deleted");
    }
}
