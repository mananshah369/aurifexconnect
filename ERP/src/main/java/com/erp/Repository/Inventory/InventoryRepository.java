package com.erp.Repository.Inventory;

import com.erp.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    /**
     * Retrieves a list of {@link Inventory} entities with the specified item name.
     *
     * @param itemName The name of the item to search for.
     * @return A list of matching {@link Inventory} entities, or an empty list if none found.
     */
    List<Inventory> findByItemName(String itemName);

    List<Inventory> findAll();
}
