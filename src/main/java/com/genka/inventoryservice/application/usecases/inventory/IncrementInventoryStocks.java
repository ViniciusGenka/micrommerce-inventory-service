package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.application.usecases.inventory.dtos.IncrementInventoryStocksInput;

public interface IncrementInventoryStocks {
    void execute(IncrementInventoryStocksInput incrementInventoryStocksInput);
}
