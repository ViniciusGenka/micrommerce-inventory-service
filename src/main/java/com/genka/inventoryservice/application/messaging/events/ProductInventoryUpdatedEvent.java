package com.genka.inventoryservice.application.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryUpdatedEvent {
    private UUID productId;
    private Integer stockQuantity;
}
