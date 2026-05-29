package com.EcomFashion.Ecommorse.Repositories.Inventory;

import com.EcomFashion.Ecommorse.Entity.Inventory.InventoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryEventRepository
        extends JpaRepository<InventoryEvent, Long> {
}
