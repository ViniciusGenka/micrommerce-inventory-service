package com.genka.inventoryservice.infra.mappers;

import com.genka.inventoryservice.domain.inventory.Inventory;
import com.genka.inventoryservice.domain.inventory.dtos.InventoryDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public InventoryDTO mapEntityToDTO(Inventory inventory) {
        return InventoryDTO.mapFromEntity(inventory);
    }
}
