package com.example.inventorymanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class InventoryManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagerApplication.class, args);
	}

	/**
	 * This bean is executed after the application starts up.
	 * It's used here to populate the in-memory H2 database with some initial items.
	 */
	@Bean
	CommandLineRunner initDatabase(InventoryRepository repository) {
		return args -> {
			System.out.println("Preloading database with initial inventory items...");
			repository.saveAll(Arrays.asList(
					new InventoryItem(null, "Laptop Pro1", "LP-001", 15, 1200.00),
					new InventoryItem(null, "Mechanical Keyboard", "MK-002", 50, 99.99),
					new InventoryItem(null, "Wireless Mouse", "WM-003", 100, 25.50)
			));
			repository.findAll().forEach(item -> System.out.println("Loaded: " + item.getName()));
		};
	}
}
