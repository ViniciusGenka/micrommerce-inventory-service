package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.exceptions.EntityNotFoundException;
import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.IncrementInventoryStocks;
import com.genka.inventoryservice.application.usecases.inventory.dtos.IncrementInventoryStocksInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.StockToIncrementInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncrementInventoryStocksImpl implements IncrementInventoryStocks {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;

    public IncrementInventoryStocksImpl(InventoryDatabaseGateway inventoryDatabaseGateway) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
    }
    @Override
    public void execute(IncrementInventoryStocksInput incrementInventoryStocksInput) {
        List<UUID> notFoundInventoryIds = new ArrayList<>();
        List<Inventory> incrementedInventories = new ArrayList<>();
        for (StockToIncrementInput stockToIncrement : incrementInventoryStocksInput.getStocksToIncrement()) {
            Optional<Inventory> optionalInventory = this.inventoryDatabaseGateway.findInventoryByProductId(stockToIncrement.getProductId());
            if (optionalInventory.isEmpty()) {
                notFoundInventoryIds.add(stockToIncrement.getProductId());
            } else {
                Inventory inventory = optionalInventory.get();
                inventory.incrementStockQuantity(stockToIncrement.getQuantityToIncrement());
                incrementedInventories.add(inventory);
            }
        }
        if (!notFoundInventoryIds.isEmpty()) {
            throw new EntityNotFoundException("Some inventories were not found: " + notFoundInventoryIds);
        }
        for (Inventory inventory : incrementedInventories) {
            this.inventoryDatabaseGateway.saveInventory(inventory);
        }
    }
}
