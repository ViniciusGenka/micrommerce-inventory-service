package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.application.usecases.inventory.dtos.UpdateInventoryInput;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;

import java.util.UUID;

public interface UpdateInventory {
    InventoryDTO execute(UUID productId, UpdateInventoryInput updateInventoryInput);
}
