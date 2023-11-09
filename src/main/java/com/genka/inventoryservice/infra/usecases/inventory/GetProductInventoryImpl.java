package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.exceptions.EntityNotFoundException;
import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.GetProductInventory;
import com.genka.inventoryservice.domain.inventory.Inventory;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;
import com.genka.inventoryservice.infra.mappers.InventoryMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProductInventoryImpl implements GetProductInventory {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;
    private final InventoryMapper inventoryMapper;

    public GetProductInventoryImpl(InventoryDatabaseGateway inventoryDatabaseGateway, InventoryMapper inventoryMapper) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryDTO execute(UUID productId) {
        Inventory inventory = this.inventoryDatabaseGateway.findInventoryByProductId(productId).orElseThrow(() -> new EntityNotFoundException("Inventory with product id " + productId + " not found"));
        return this.inventoryMapper.mapEntityToDTO(inventory);
    }
}
