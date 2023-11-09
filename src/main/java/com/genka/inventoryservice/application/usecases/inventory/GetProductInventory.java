package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;

import java.util.UUID;

public interface GetProductInventory {
    InventoryDTO execute(UUID productId);
}
