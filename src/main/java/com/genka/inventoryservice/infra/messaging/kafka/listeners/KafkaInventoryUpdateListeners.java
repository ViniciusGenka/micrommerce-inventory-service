package com.genka.inventoryservice.infra.messaging.kafka.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genka.inventoryservice.application.messaging.events.ProductInventoryUpdatedEvent;
import com.genka.inventoryservice.application.usecases.inventory.UpdateInventory;
import com.genka.inventoryservice.application.usecases.inventory.dtos.UpdateInventoryInput;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaInventoryUpdateListeners {

    private final UpdateInventory updateInventoryUseCase;
    private final ObjectMapper mapper;

    public KafkaInventoryUpdateListeners(UpdateInventory updateInventoryUseCase, ObjectMapper mapper) {
        this.updateInventoryUseCase = updateInventoryUseCase;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "product_inventory_updated", groupId = "group-1")
    public void updateInventory(String message) {
        try {
             ProductInventoryUpdatedEvent productInventoryUpdatedEvent = mapper.readValue(message, ProductInventoryUpdatedEvent.class);
             UpdateInventoryInput updateInventoryInput = UpdateInventoryInput.builder()
                     .stockQuantity(productInventoryUpdatedEvent.getStockQuantity())
                     .build();
             this.updateInventoryUseCase.execute(productInventoryUpdatedEvent.getProductId(), updateInventoryInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

