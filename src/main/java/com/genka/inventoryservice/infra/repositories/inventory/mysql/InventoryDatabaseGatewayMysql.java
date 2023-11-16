package com.genka.inventoryservice.infra.repositories.inventory.mysql;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class InventoryDatabaseGatewayMysql implements InventoryDatabaseGateway {

    private final InventoryRepositoryMysql inventoryRepository;

    public InventoryDatabaseGatewayMysql(InventoryRepositoryMysql inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Optional<Inventory> findInventoryByProductId(UUID productId) {
        return this.inventoryRepository.findByProductId(productId);
    }

    @Override
    public List<Inventory> findInventoriesByProductIdIn(List<UUID> productIds) {
        return this.inventoryRepository.findByProductIdIn(productIds);
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        return this.inventoryRepository.save(inventory);
    }
}
