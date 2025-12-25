package com.retailstockpro.inventory.service.impl;

import com.retailstockpro.inventory.exception.ResourceNotFoundException;
import com.retailstockpro.inventory.model.Product;
import com.retailstockpro.inventory.repository.ProductRepository;
import com.retailstockpro.inventory.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@org.springframework.transaction.annotation.Transactional
@SuppressWarnings("null")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantityInStock(productDetails.getQuantityInStock());
        product.setLowStockThreshold(productDetails.getLowStockThreshold());
        product.setCategory(productDetails.getCategory());
        product.setSupplier(productDetails.getSupplier());

        return productRepository.save(product);
    }

    @Override
    @SuppressWarnings("null")
    public void deleteProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    public List<Product> getLowStockProducts() {
        // Can be optimized with custom query
        return productRepository.findLowStockProducts();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        if (keyword != null) {
            return productRepository.findByNameContainingIgnoreCase(keyword);
        }
        return productRepository.findAll();
    }

    @Override
    public long getTotalProductCount() {
        return productRepository.count();
    }

    @Override
    public long getLowStockCount() {
        return productRepository.findLowStockProducts().size(); // Or count query
    }

    @Override
    public BigDecimal getTotalInventoryValue() {
        return productRepository.findAll().stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantityInStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
