package com.genka.inventoryservice.domain.inventory.dtos;

import com.genka.inventoryservice.domain.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {

    private UUID id;
    private UUID productId;
    private Integer stockQuantity;
    private Integer version;

    public static InventoryDTO mapFromEntity(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .stockQuantity(inventory.getStockQuantity())
                .version(inventory.getVersion())
                .build();
    }
}