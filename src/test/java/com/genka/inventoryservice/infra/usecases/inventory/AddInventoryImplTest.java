package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import com.genka.inventoryservice.infra.mappers.InventoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddInventoryImplTest {

    @Mock
    private InventoryDatabaseGateway inventoryDatabaseGatewayMock;
    @Mock
    private InventoryMapper inventoryMapperMock;
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
        Inventory savedInventory = Inventory.builder()
                .id(UUID.randomUUID())
                .productId(input.getProductId())
                .stockQuantity(input.getStockQuantity())
                .build();
        when(inventoryDatabaseGatewayMock.saveInventory(inventoryToSave)).thenReturn(savedInventory);
        sut.execute(input);
        verify(inventoryDatabaseGatewayMock, times(1)).saveInventory(inventoryToSave);
        verify(inventoryMapperMock, times(1)).mapEntityToDTO(savedInventory);
    }

}