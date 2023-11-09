package com.genka.inventoryservice.application.usecases.inventory;

import com.genka.inventoryservice.application.usecases.inventory.dtos.GetInventoryAvailabilitiesInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.InventoryAvailabilityResponse;

import java.util.List;

public interface GetInventoryAvailabilities {
    List<InventoryAvailabilityResponse> execute(GetInventoryAvailabilitiesInput GetInventoryAvailabilitiesInput);
}
