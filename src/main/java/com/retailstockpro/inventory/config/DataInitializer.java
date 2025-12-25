package com.retailstockpro.inventory.config;

import com.retailstockpro.inventory.model.Role;
import com.retailstockpro.inventory.model.User;
import com.retailstockpro.inventory.model.Category;
import com.retailstockpro.inventory.model.Supplier;
import com.retailstockpro.inventory.repository.UserRepository;
import com.retailstockpro.inventory.repository.CategoryRepository;
import com.retailstockpro.inventory.repository.SupplierRepository;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Init Users
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@retail.com");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user@retail.com");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
        }

        // Init Categories
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Electronics", "Gadgets and devices"));
            categoryRepository.save(new Category(null, "Grocery", "Daily essentials"));
            categoryRepository.save(new Category(null, "Clothing", "Apparel and fashion"));
        }

        // Init Suppliers
        if (supplierRepository.count() == 0) {
            supplierRepository.save(new Supplier(null, "TechWorld Inc.", "John Doe", "john@techworld.com",
                    "1234567890", "123 Tech St"));
            supplierRepository.save(new Supplier(null, "FreshProduce Co.", "Jane Smith", "jane@fresh.com",
                    "0987654321", "456 Farm Rd"));
        }
    }
}
