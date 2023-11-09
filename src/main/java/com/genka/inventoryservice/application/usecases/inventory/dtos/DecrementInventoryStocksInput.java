package com.genka.inventoryservice.application.usecases.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecrementInventoryStocksInput {
    private List<StockToDecrementInput> stocksToDecrement;
}
