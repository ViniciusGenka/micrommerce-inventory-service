package com.genka.inventoryservice.infra.usecases.inventory;

import com.genka.inventoryservice.application.exceptions.EntityNotFoundException;
import com.genka.inventoryservice.application.gateways.InventoryDatabaseGateway;
import com.genka.inventoryservice.application.usecases.inventory.dtos.DecrementInventoryStocksInput;
import com.genka.inventoryservice.application.usecases.inventory.dtos.StockToDecrementInput;
import com.genka.inventoryservice.domain.inventory.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DecrementInventoryStocksImplTest {
    @Mock
    private InventoryDatabaseGateway inventoryDatabaseGatewayMock;
    @InjectMocks
    private DecrementInventoryStocksImpl sut;

    @Test
    @DisplayName("It should call the 'decrementStockQuantity' method on the Inventory entities and then save them if all inventories are found")
    void allInventoriesFound() {
        Inventory inventory1 = Inventory.builder()
                .id(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .stockQuantity(10)
                .build();
        Inventory inventory2 = Inventory.builder()
                .id(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .stockQuantity(10)
                .build();
        StockToDecrementInput stockToDecrement1 = StockToDecrementInput.builder()
                .productId(inventory1.getProductId())
                .quantityToDecrement(1)
                .build();
        StockToDecrementInput stockToDecrement2 = StockToDecrementInput.builder()
                .productId(inventory2.getProductId())
                .quantityToDecrement(1)
                .build();
        DecrementInventoryStocksInput input = DecrementInventoryStocksInput.builder()
                .stocksToDecrement(Arrays.asList(stockToDecrement1, stockToDecrement2))
                .build();
        Inventory inventoryToSave1 = Inventory.builder()
                .id(inventory1.getId())
                .productId(inventory1.getProductId())
                .stockQuantity(inventory1.getStockQuantity() - stockToDecrement1.getQuantityToDecrement())
                .build();
        Inventory inventoryToSave2 = Inventory.builder()
                .id(inventory2.getId())
                .productId(inventory2.getProductId())
                .stockQuantity(inventory1.getStockQuantity() - stockToDecrement2.getQuantityToDecrement())
                .build();
        Inventory inventory1Spy = spy(inventory1);
        Inventory inventory2Spy = spy(inventory2);
        when(inventoryDatabaseGatewayMock.findInventoryByProductId(inventory1.getProductId())).thenReturn(Optional.of(inventory1Spy));
        when(inventoryDatabaseGatewayMock.findInventoryByProductId(inventory2.getProductId())).thenReturn(Optional.of(inventory2Spy));
        sut.execute(input);
        verify(inventory1Spy, times(1)).decrementStockQuantity(1);
        verify(inventory2Spy, times(1)).decrementStockQuantity(1);
        verify(inventoryDatabaseGatewayMock, times(1)).saveInventory(inventoryToSave1);
        verify(inventoryDatabaseGatewayMock, times(1)).saveInventory(inventoryToSave2);
    }

    @Test
    @DisplayName("It should throw an exception if any Inventory is not found")
    void someInventoryNotFound() {
        UUID nonexistentProductId = UUID.randomUUID();
        StockToDecrementInput stockToDecrement = StockToDecrementInput.builder()
                .productId(nonexistentProductId)
                .quantityToDecrement(1)
                .build();
        DecrementInventoryStocksInput input = DecrementInventoryStocksInput.builder()
                .stocksToDecrement(Collections.singletonList(stockToDecrement))
                .build();
        when(inventoryDatabaseGatewayMock.findInventoryByProductId(nonexistentProductId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            sut.execute(input);
        });
    }
}