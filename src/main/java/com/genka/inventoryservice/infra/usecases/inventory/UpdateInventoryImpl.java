package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.exceptions.EntityNotFoundException;
import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.UpdateInventory;
import com.genka.inventoryservice.application.usecases.inventory.dtos.UpdateInventoryInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;
import com.genka.inventoryservice.infra.mappers.InventoryMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateInventoryImpl implements UpdateInventory {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;
    private final ModelMapper modelMapper;
    private final InventoryMapper inventoryMapper;

    public UpdateInventoryImpl(InventoryDatabaseGateway inventoryDatabaseGateway, ModelMapper modelMapper, InventoryMapper inventoryMapper) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
        this.modelMapper = modelMapper;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public InventoryDTO execute(UUID productId, UpdateInventoryInput updateInventoryInput) {
        Inventory inventory = this.inventoryDatabaseGateway.findInventoryByProductId(productId).orElseThrow(() -> new EntityNotFoundException("Inventory with product ID " + productId + " not found"));
        this.modelMapper.map(updateInventoryInput, inventory);
        this.inventoryDatabaseGateway.saveInventory(inventory);
        return this.inventoryMapper.mapEntityToDTO(inventory);
    }
}
