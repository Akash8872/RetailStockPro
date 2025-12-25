package com.retailstockpro.inventory.service;

import com.retailstockpro.inventory.model.Supplier;
import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAllSuppliers();

    Optional<Supplier> getSupplierById(Long id);

    Supplier saveSupplier(Supplier supplier);

    void deleteSupplier(Long id);
}
