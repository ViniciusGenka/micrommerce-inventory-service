package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;

public interface AddInventory {
    InventoryDTO execute(AddInventoryInput addInventoryInput);
}
