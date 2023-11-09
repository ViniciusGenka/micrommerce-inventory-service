package com.genka.inventoryservice.infra.repositories.inventory.mysql;

import com.genka.inventoryservice.domain.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepositoryMysql extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByProductId(UUID productId);

    List<Inventory> findByProductIdIn(List<UUID> productIds);
}
