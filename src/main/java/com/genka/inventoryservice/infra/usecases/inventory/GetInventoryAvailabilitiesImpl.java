package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.GetInventoryAvailabilities;
import com.genka.inventoryservice.application.usecases.inventory.dtos.GetInventoryAvailabilitiesInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.InventoryAvailabilityResponse;
import com.genka.inventoryservice.application.usecases.inventory.dtos.StockToCheckInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetInventoryAvailabilitiesImpl implements GetInventoryAvailabilities {

    private final InventoryDatabaseGateway inventoryDatabaseGateway;

    public GetInventoryAvailabilitiesImpl(InventoryDatabaseGateway inventoryDatabaseGateway) {
        this.inventoryDatabaseGateway = inventoryDatabaseGateway;
    }

    @Override
    public List<InventoryAvailabilityResponse> execute(GetInventoryAvailabilitiesInput GetInventoryAvailabilitiesInput) {
        List<UUID> productIdsToCheck = GetInventoryAvailabilitiesInput.getStocksToCheck().stream().map(StockToCheckInput::getProductId).toList();
        List<Inventory> productInventories = this.inventoryDatabaseGateway.findInventoriesByProductIdIn(productIdsToCheck);
        return GetInventoryAvailabilitiesInput.getStocksToCheck().stream()
                .map(stockToCheck -> {
                    boolean availability = productInventories.stream()
                            .anyMatch(inventory -> inventory.getProductId().equals(stockToCheck.getProductId()) && inventory.getStockQuantity() >= stockToCheck.getQuantityToCheck());
                    return new InventoryAvailabilityResponse(stockToCheck.getProductId(), availability);
                })
                .toList();
    }
}
