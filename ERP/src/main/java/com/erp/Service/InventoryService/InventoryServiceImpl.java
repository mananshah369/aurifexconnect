package com.erp.Service.InventoryService;

import com.erp.Dto.Request.InventoryRequest;
import com.erp.Dto.Response.InventoryResponse;
import com.erp.Exception.Inventory_Exception.InventoryNotFoundException;
import com.erp.Mapper.Inventory.InventoryMapper;
import com.erp.Model.Inventory;
import com.erp.Repository.Inventory.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse addItem(InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryMapper.mapToInventory(inventoryRequest);
        inventoryRepository.save(inventory);
        return inventoryMapper.mapToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse updateItem(InventoryRequest inventoryRequest, long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found , invalid id "));

        inventoryMapper.mapToInventoryEntity(inventoryRequest,inventory);

        inventoryRepository.save(inventory);
        return inventoryMapper.mapToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse findByItemId(long itemId) {
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found , invalid id "));
        return inventoryMapper.mapToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse deleteByItemId(long itemId) {
        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found , invalid id "));

//        inventory.setDeleted(true);
//        inventoryRepository.save(inventory);

        inventoryRepository.deleteById(itemId);
        return inventoryMapper.mapToInventoryResponse(inventory);
    }

    @Override
    public List<InventoryResponse> findByItemName(String itemName) {
        List<Inventory> inventory = inventoryRepository.findByItemName(itemName);
        return inventoryMapper.mapToInventoryResponse(inventory);
    }

    @Override
    public List<InventoryResponse> findByAll() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventoryMapper.mapToInventoryResponse(inventories);
    }
}
