package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddInventoryImplTest {

    @Mock
    private InventoryDatabaseGateway inventoryDatabaseGatewayMock;
    @InjectMocks
    private AddInventoryImpl sut;

    @Test
    @DisplayName("It should call the 'saveInventory' method from InventoryDatabaseGateway")
    void saveInventory() {
        AddInventoryInput input = AddInventoryInput.builder()
                .productId(UUID.randomUUID())
                .stockQuantity(1)
                .build();
        Inventory inventoryToSave = Inventory.builder()
                .productId(input.getProductId())
                .stockQuantity(input.getStockQuantity())
                .build();
        sut.execute(input);
        verify(inventoryDatabaseGatewayMock, times(1)).saveInventory(inventoryToSave);
    }

}