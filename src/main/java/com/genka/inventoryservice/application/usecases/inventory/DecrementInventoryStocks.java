package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.application.usecases.inventory.dtos.DecrementInventoryStocksInput;

public interface DecrementInventoryStocks {
    void execute(DecrementInventoryStocksInput decrementInventoryStocksInput);
}
