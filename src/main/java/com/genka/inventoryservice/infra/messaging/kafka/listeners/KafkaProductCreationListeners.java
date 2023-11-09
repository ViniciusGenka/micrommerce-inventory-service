package com.genka.inventoryservice.infra.messaging.kafka.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genka.inventoryservice.application.usecases.inventory.AddInventory;
import com.genka.inventoryservice.application.usecases.inventory.dtos.AddInventoryInput;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaProductCreationListeners {

    private final AddInventory addInventoryUseCase;
    private final ObjectMapper mapper;

    public KafkaProductCreationListeners(AddInventory addInventoryUseCase, ObjectMapper mapper) {
        this.addInventoryUseCase = addInventoryUseCase;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "product_created", groupId = "group-1")
    public void addInventory(String message) {
        try {
            AddInventoryInput addInventoryInput = mapper.readValue(message, AddInventoryInput.class);
            this.addInventoryUseCase.execute(addInventoryInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
