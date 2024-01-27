package com.genka.inventoryservice.application.controllers;

import com.genka.inventoryservice.application.usecases.inventory.dtos.*;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface InventoryController {
    ResponseEntity<InventoryDTO> addInventory(AddInventoryInput addInventoryInput);

    ResponseEntity<List<InventoryAvailabilityResponse>> getInventoryAvailabilities(GetInventoryAvailabilitiesInput GetInventoryAvailabilitiesInput);

    ResponseEntity<Void> decrementInventoryStocks(DecrementInventoryStocksInput decrementInventoryStocksInput);

    ResponseEntity<Void> incrementInventoryStocks(IncrementInventoryStocksInput incrementInventoryStocksInput);

    ResponseEntity<InventoryDTO> getProductInventory(UUID productId);
}
