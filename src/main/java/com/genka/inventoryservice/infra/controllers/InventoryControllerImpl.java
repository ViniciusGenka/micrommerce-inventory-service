package com.genka.inventoryservice.infra.controllers;

import com.genka.inventoryservice.application.controllers.InventoryController;
import com.genka.inventoryservice.application.usecases.inventory.*;
import com.genka.inventoryservice.application.usecases.inventory.dtos.*;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventories")
public class InventoryControllerImpl implements InventoryController {

    private final AddInventory addInventoryUseCase;
    private final GetInventoryAvailabilities getInventoryAvailabilitiesUseCase;
    private final DecrementInventoryStocks decrementInventoryStocksUseCase;
    private final IncrementInventoryStocks incrementInventoryStocksUseCase;

    private final GetProductInventory getProductInventoryUseCase;


    public InventoryControllerImpl(AddInventory addInventoryUseCase, GetInventoryAvailabilities getInventoryAvailabilitiesUseCase, DecrementInventoryStocks decrementInventoryStocks, IncrementInventoryStocks incrementInventoryStocksUseCase, GetProductInventory getProductInventoryUseCase) {
        this.addInventoryUseCase = addInventoryUseCase;
        this.getInventoryAvailabilitiesUseCase = getInventoryAvailabilitiesUseCase;
        this.decrementInventoryStocksUseCase = decrementInventoryStocks;
        this.incrementInventoryStocksUseCase = incrementInventoryStocksUseCase;
        this.getProductInventoryUseCase = getProductInventoryUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InventoryDTO> addInventory(@RequestBody AddInventoryInput addInventoryInput) {
        InventoryDTO savedInventory = this.addInventoryUseCase.execute(addInventoryInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
    }

    @Override
    @PostMapping("/availabilities")
    public ResponseEntity<List<InventoryAvailabilityResponse>> getInventoryAvailabilities(@RequestBody GetInventoryAvailabilitiesInput getInventoryAvailabilitiesInput) {
        List<InventoryAvailabilityResponse> availabilityResponses = this.getInventoryAvailabilitiesUseCase.execute(getInventoryAvailabilitiesInput);
        return ResponseEntity.status(HttpStatus.OK).body(availabilityResponses);
    }

    @Override
    @PostMapping("/stocks/decrement")
    public ResponseEntity<Void> decrementInventoryStocks(@RequestBody DecrementInventoryStocksInput decrementInventoryStocksInput) {
        this.decrementInventoryStocksUseCase.execute(decrementInventoryStocksInput);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PostMapping("/stocks/increment")
    public ResponseEntity<Void> incrementInventoryStocks(@RequestBody IncrementInventoryStocksInput incrementInventoryStocksInput) {
        this.incrementInventoryStocksUseCase.execute(incrementInventoryStocksInput);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @GetMapping("/products/{id}")
    public ResponseEntity<InventoryDTO> getProductInventory(@PathVariable UUID id) {
        InventoryDTO inventory = this.getProductInventoryUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }
}
