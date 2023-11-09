package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.exceptions.EntityNotFoundException;
import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.domain.inventory.Inventory;
import com.genka.inventoryservice.infra.mappers.InventoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductInventoryImplTest {

    @Mock
    private InventoryDatabaseGateway inventoryDatabaseGatewayMock;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private GetProductInventoryImpl sut;

    @Test
    @DisplayName("It should call the 'findInventoryByProductId' method from InventoryDatabaseGateway and then return a mapped InventoryDTO")
    void getMappedProductInventoryIfFound() {
        UUID existingProductId = UUID.randomUUID();
        Inventory inventory = Inventory.builder()
                .id(existingProductId)
                .productId(existingProductId)
                .stockQuantity(10)
                .build();
        when(inventoryDatabaseGatewayMock.findInventoryByProductId(existingProductId)).thenReturn(Optional.of(inventory));
        sut.execute(existingProductId);
        verify(inventoryDatabaseGatewayMock, times(1)).findInventoryByProductId(existingProductId);
        verify(inventoryMapper, times(1)).mapEntityToDTO(inventory);
    }

    @Test
    @DisplayName("It should throw an exception if the inventory is not found")
    void inventoryNotFound() {
        UUID nonexistentProductId = UUID.randomUUID();
        when(inventoryDatabaseGatewayMock.findInventoryByProductId(nonexistentProductId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            sut.execute(nonexistentProductId);
        });
    }

}