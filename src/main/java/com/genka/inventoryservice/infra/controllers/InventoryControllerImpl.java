package com.genka.inventoryservice.infra.controllers;

import com.genka.inventoryservice.application.controllers.InventoryController;
import com.genka.inventoryservice.application.usecases.inventory.AddInventory;
import com.genka.inventoryservice.application.usecases.inventory.GetInventoryAvailabilities;
import com.genka.inventoryservice.application.usecases.inventory.DecrementInventoryStocks;
import com.genka.inventoryservice.application.usecases.inventory.GetProductInventory;
import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.GetInventoryAvailabilitiesInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.DecrementInventoryStocksInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.InventoryAvailabilityResponse;
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

    private final GetProductInventory getProductInventoryUseCase;


    public InventoryControllerImpl(AddInventory addInventoryUseCase, GetInventoryAvailabilities getInventoryAvailabilitiesUseCase, DecrementInventoryStocks decrementInventoryStocks, GetProductInventory getProductInventoryUseCase) {
        this.addInventoryUseCase = addInventoryUseCase;
        this.getInventoryAvailabilitiesUseCase = getInventoryAvailabilitiesUseCase;
        this.decrementInventoryStocksUseCase = decrementInventoryStocks;
        this.getProductInventoryUseCase = getProductInventoryUseCase;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addInventory(@RequestBody AddInventoryInput addInventoryInput) {
        this.addInventoryUseCase.execute(addInventoryInput);
    }

    @Override
    @PostMapping("/availabilities")
    public ResponseEntity<List<InventoryAvailabilityResponse>> getInventoryAvailabilities(@RequestBody GetInventoryAvailabilitiesInput GetInventoryAvailabilitiesInput) {
        List<InventoryAvailabilityResponse> availabilityResponses = this.getInventoryAvailabilitiesUseCase.execute(GetInventoryAvailabilitiesInput);
        return ResponseEntity.status(HttpStatus.OK).body(availabilityResponses);
    }

    @Override
    @PostMapping("/stocks/decrement")
    public ResponseEntity<Void> decrementInventoryStocks(@RequestBody DecrementInventoryStocksInput decrementInventoryStocksInput) {
        this.decrementInventoryStocksUseCase.execute(decrementInventoryStocksInput);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @GetMapping("/products/{id}")
    public ResponseEntity<InventoryDTO> getProductInventory(@PathVariable UUID id) {
        InventoryDTO inventory = this.getProductInventoryUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }
}
