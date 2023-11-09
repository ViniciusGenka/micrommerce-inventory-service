package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.dtos.GetInventoryAvailabilitiesInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.InventoryAvailabilityResponse;
import com.genka.inventoryservice.application.usecases.inventory.dtos.StockToCheckInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GetInventoryAvailabilitiesImplTest {
    @Mock
    private InventoryDatabaseGateway inventoryDatabaseGatewayMock;
    @InjectMocks
    private GetInventoryAvailabilitiesImpl sut;

    @Test
    @DisplayName("It should return the product availability as true if an available quantity is provided")
    void getAvailabilityFromAvailableProduct() {
        UUID existingProductId = UUID.randomUUID();
        Inventory inventory = Inventory.builder()
                .id(UUID.randomUUID())
                .productId(existingProductId)
                .stockQuantity(10)
                .build();
        StockToCheckInput stockToCheckInput = StockToCheckInput.builder()
                .productId(existingProductId)
                .quantityToCheck(5)
                .build();
        GetInventoryAvailabilitiesInput input = GetInventoryAvailabilitiesInput.builder()
                .stocksToCheck(Collections.singletonList(stockToCheckInput))
                .build();
        when(inventoryDatabaseGatewayMock.findInventoriesByProductIdIn(Collections.singletonList(existingProductId))).thenReturn(Collections.singletonList(inventory));
        List<InventoryAvailabilityResponse> sutResponse = sut.execute(input);
        assertTrue(sutResponse.get(0).getAvailability());
    }

    @Test
    @DisplayName("It should return the product availability as false if an unavailable quantity is provided")
    void getAvailabilityFromUnavailableProduct() {
        UUID existingProductId = UUID.randomUUID();
        Inventory inventory = Inventory.builder()
                .id(UUID.randomUUID())
                .productId(existingProductId)
                .stockQuantity(10)
                .build();
        StockToCheckInput stockToCheckInput = StockToCheckInput.builder()
                .productId(existingProductId)
                .quantityToCheck(999999999)
                .build();
        GetInventoryAvailabilitiesInput input = GetInventoryAvailabilitiesInput.builder()
                .stocksToCheck(Collections.singletonList(stockToCheckInput))
                .build();
        when(inventoryDatabaseGatewayMock.findInventoriesByProductIdIn(Collections.singletonList(existingProductId))).thenReturn(Collections.singletonList(inventory));
        List<InventoryAvailabilityResponse> sutResponse = sut.execute(input);
        assertFalse(sutResponse.get(0).getAvailability());
    }
}