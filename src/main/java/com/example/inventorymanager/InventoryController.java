package com.example.inventorymanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing inventory items.
 * The base path for all endpoints is /api/v1/inventory.
 */
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryRepository repository;

    @Autowired
    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    // CREATE: POST /api/v1/inventory
    @PostMapping
    public InventoryItem createItem(@RequestBody InventoryItem newItem) {
        return repository.save(newItem);
    }

    // READ ALL: GET /api/v1/inventory
    @GetMapping
    public List<InventoryItem> getAllItems() {
        return repository.findAll();
    }

    // READ ONE: GET /api/v1/inventory/{id}
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE: PUT /api/v1/inventory/{id}
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id, @RequestBody InventoryItem itemDetails) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(itemDetails.getName());
                    item.setSku(itemDetails.getSku());
                    item.setQuantity(itemDetails.getQuantity());
                    item.setPrice(itemDetails.getPrice());
                    InventoryItem updatedItem = repository.save(item);
                    return ResponseEntity.ok(updatedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: DELETE /api/v1/inventory/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}