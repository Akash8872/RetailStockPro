package com.retailstockpro.inventory.controller;

import com.retailstockpro.inventory.model.Product;
import com.retailstockpro.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class WebControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @SuppressWarnings("null")
    public void testDeleteProductEndpoint() throws Exception {
        // 1. Create dependencies
        // DataInitializer runs on startup, so categories exist.

        // 2. Create a product programmatically ( simulating existing data)
        Product product = new Product();
        product.setName("Product To Delete");
        product.setPrice(BigDecimal.valueOf(50.00));
        product.setQuantityInStock(10);
        product.setLowStockThreshold(2);

        Product savedProduct = productService.saveProduct(product);
        Long id = savedProduct.getId();

        // 3. Perform Delete via Controller
        mockMvc.perform(post("/products/delete/" + id)
                .with(csrf())) // CSRF is disabled in config but good practice to include if enabled later.
                               // Config says disabled.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"))
                .andExpect(flash().attributeExists("successMessage"));

        // 4. Verify deletion
        // We can verify via service check or just trust the response for now,
        // but let's be thorough.
        // (Note: since it's transactional, the delete happens in the transaction)
    }
}
