package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.AddInventory;
import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.springframework.stereotype.Service;

@Service
public class AddInventoryImpl implements AddInventory {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;

    public AddInventoryImpl(InventoryDatabaseGateway inventoryDatabaseGateway) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
    }

    @Override
    public void execute(AddInventoryInput addInventoryInput) {
        Inventory inventory = Inventory.builder()
                .productId(addInventoryInput.getProductId())
                .stockQuantity(addInventoryInput.getStockQuantity())
                .build();
        this.inventoryDatabaseGateway.saveInventory(inventory);
    }
}
