package com.genka.inventoryservice.application.gateways;

import com.genka.inventoryservice.domain.inventory.Inventory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryDatabaseGateway {

    Optional<Inventory> findInventoryByProductId(UUID productId);

    List<Inventory> findInventoriesByProductIdIn(List<UUID> productIds);


    Inventory saveInventory(Inventory inventory);
}
