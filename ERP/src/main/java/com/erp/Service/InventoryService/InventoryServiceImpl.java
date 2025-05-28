package com.erp.Service.InventoryService;

import com.erp.Dto.Request.InventoryRequest;
import com.erp.Dto.Response.InventoryResponse;
import com.erp.Exception.Branch_Exception.BranchNotFoundException;
import com.erp.Exception.Inventory_Exception.InventoryNotFoundException;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Mapper.Inventory.InventoryMapper;
import com.erp.Model.Branch;
import com.erp.Model.Inventory;
import com.erp.Repository.Branch.BranchRepository;
import com.erp.Repository.Inventory.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final BranchRepository branchRepository;

    @Override
    public InventoryResponse addItem(InventoryRequest inventoryRequest , long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(()-> new BranchNotFoundException("Branch Not found , Invalid Branch Id"));
        Inventory inventory = inventoryMapper.mapToInventory(inventoryRequest);
        inventory.setBranch(branch);
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
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found , invalid id "+itemId));

//        inventory.setDeleted(true);
//        inventoryRepository.save(inventory);

        inventoryRepository.deleteById(itemId);
        return inventoryMapper.mapToInventoryResponse(inventory);
    }

    @Override
    public List<InventoryResponse> findByItemName(String itemName) {
        List<Inventory> inventory = inventoryRepository.findByItemName(itemName);

        if (inventory.isEmpty()) {
            throw new InventoryNotFoundException("No Inventory Not Found By Name " + itemName);
        }else {
            return inventoryMapper.mapToInventoryResponse(inventory);
        }
    }

    @Override
    public List<InventoryResponse> findByAll() {
        List<Inventory> inventories = inventoryRepository.findAll();

        if (inventories.isEmpty()) {
            throw new InventoryNotFoundException("No Inventories Not Found");
        }else {
            return inventoryMapper.mapToInventoryResponse(inventories);
        }
    }
}
