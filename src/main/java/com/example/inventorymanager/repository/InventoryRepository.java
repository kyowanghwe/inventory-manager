package com.example.inventorymanager.repository;

import com.example.inventorymanager.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for InventoryItem.
 * Provides all standard CRUD database operations (save, find, delete, etc.) automatically.
 */
@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    // Custom query methods can be added here if needed,
    // e.g., List<InventoryItem> findBySku(String sku);
}