package com.inventory.inventory.repo;

import com.inventory.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "SELECT * FROM Inventory WHERE itemId=?1",nativeQuery = true)
    Inventory getItemById(Integer itemId);
}
