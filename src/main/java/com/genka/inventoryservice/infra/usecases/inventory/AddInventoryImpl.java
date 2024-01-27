package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.AddInventory;
import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;
import com.genka.inventoryservice.infra.mappers.InventoryMapper;
import org.springframework.stereotype.Service;

@Service
public class AddInventoryImpl implements AddInventory {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;
    private final InventoryMapper inventoryMapper;

    public AddInventoryImpl(InventoryDatabaseGateway inventoryDatabaseGateway, InventoryMapper inventoryMapper) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryDTO execute(AddInventoryInput addInventoryInput) {
        boolean inventoryAlreadyExists = this.inventoryDatabaseGateway.findInventoryByProductId(addInventoryInput.getProductId()).isPresent();
        if(inventoryAlreadyExists) {
            throw new IllegalArgumentException("inventory already exists");
        }
        Inventory inventory = Inventory.builder()
                .productId(addInventoryInput.getProductId())
                .stockQuantity(addInventoryInput.getStockQuantity())
                .build();
        return this.inventoryMapper.mapEntityToDTO(this.inventoryDatabaseGateway.saveInventory(inventory));
    }
}
