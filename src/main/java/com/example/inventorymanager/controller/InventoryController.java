package com.example.inventorymanager.controller;

import com.example.inventorymanager.InventoryItem;
import com.example.inventorymanager.repository.InventoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory Controller", description = "Operations related to inventory management")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryRepository repository;

    @Autowired
    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Operation(summary = "Create a new inventory item", description = "Adds a new item to the inventory database")
    @PostMapping
    public InventoryItem createItem(@RequestBody InventoryItem newItem) {
        log.info("Request to create item: {}", newItem);
        InventoryItem saved = repository.save(newItem);
        log.info("Item created with ID: {}", saved.getId());
        return saved;
    }

    // READ ALL
    @Operation(summary = "Get all inventory items", description = "Retrieves a list of all items in inventory")
    @GetMapping
    public List<InventoryItem> getAllItems() {
        log.info("Fetching all inventory items");
        List<InventoryItem> items = repository.findAll();
        log.info("Fetched {} items", items.size());
        return items;
    }

    // READ ONE
    @Operation(summary = "Get item by ID", description = "Fetches a single inventory item using its ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        log.info("Fetching item with ID: {}", id);
        return repository.findById(id)
                .map(item -> {
                    log.info("Item found: {}", item);
                    return ResponseEntity.ok(item);
                })
                .orElseGet(() -> {
                    log.warn("Item with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // UPDATE
    @Operation(summary = "Update item by ID", description = "Updates an existing inventory item using its ID")
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id, @RequestBody InventoryItem itemDetails) {
        log.info("Updating item with ID: {}", id);
        return repository.findById(id)
                .map(item -> {
                    log.debug("Current item: {}", item);
                    log.debug("New details: {}", itemDetails);

                    item.setName(itemDetails.getName());
                    item.setSku(itemDetails.getSku());
                    item.setQuantity(itemDetails.getQuantity());
                    item.setPrice(itemDetails.getPrice());

                    InventoryItem updated = repository.save(item);
                    log.info("Item updated: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    log.warn("Cannot update. Item with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // DELETE
    @Operation(summary = "Delete item by ID", description = "Removes an inventory item from the database using its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.info("Deleting item with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Item {} deleted", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Cannot delete. Item with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
