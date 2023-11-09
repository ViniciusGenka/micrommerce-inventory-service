package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;

public interface AddInventory {
    void execute(AddInventoryInput addInventoryInput);
}
