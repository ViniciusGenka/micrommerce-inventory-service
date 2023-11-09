package com.genka.inventoryservice.domain.inventory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "inventories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID productId;
    private Integer stockQuantity;

    @Version
    private Integer version;

    public void decrementStockQuantity(Integer quantityToDecrement) {
        this.stockQuantity -= quantityToDecrement;
    }

}
