package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.exceptions.EntityNotFoundException;
import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.DecrementInventoryStocks;
import com.genka.inventoryservice.application.usecases.inventory.dtos.DecrementInventoryStocksInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.StockToDecrementInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DecrementInventoryStocksImpl implements DecrementInventoryStocks {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;

    public DecrementInventoryStocksImpl(InventoryDatabaseGateway inventoryDatabaseGateway) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
    }

    @Override
    @Transactional
    public void execute(DecrementInventoryStocksInput decrementInventoryStocksInput) {
        List<UUID> notFoundInventoryIds = new ArrayList<>();
        List<Inventory> decrementedInventories = new ArrayList<>();
        for (StockToDecrementInput stockToDecrement : decrementInventoryStocksInput.getStocksToDecrement()) {
            Optional<Inventory> optionalInventory = this.inventoryDatabaseGateway.findInventoryByProductId(stockToDecrement.getProductId());
            if (optionalInventory.isEmpty()) {
                notFoundInventoryIds.add(stockToDecrement.getProductId());
            } else {
                Inventory inventory = optionalInventory.get();
                inventory.decrementStockQuantity(stockToDecrement.getQuantityToDecrement());
                decrementedInventories.add(inventory);
            }
        }
        if (!notFoundInventoryIds.isEmpty()) {
            throw new EntityNotFoundException("Some inventories were not found: " + notFoundInventoryIds);
        }
        for (Inventory inventory : decrementedInventories) {
            this.inventoryDatabaseGateway.saveInventory(inventory);
        }
    }
}
