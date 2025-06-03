package com.erp.Service.LineItems;

import com.erp.Dto.Request.LineItemsRequest;
import com.erp.Dto.Response.LineItemsResponse;
import com.erp.Enum.VoucherType;
import com.erp.Exception.Inventory_Exception.InsufficientStockException;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Exception.Master.MasterNotFoundException;
import com.erp.Exception.Voucher.VoucherNotFound;
import com.erp.Mapper.LineItems.LineItemsMapper;
import com.erp.Model.Inventory;
import com.erp.Model.LineItems;
import com.erp.Model.Master;
import com.erp.Repository.Inventory.InventoryRepository;
import com.erp.Repository.LineItems.LineItemsRepository;
import com.erp.Repository.Master.MasterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LineItemsServiceImpl implements LineItemService {

    private final LineItemsRepository lineItemsRepository;
    private final LineItemsMapper lineItemsMapper;
    private final InventoryRepository inventoryRepository;
    private final MasterRepository masterRepository;

    @Override
    @Transactional
    public LineItemsResponse createLineItems(LineItemsRequest request) {

        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new MasterNotFoundException("Invalid Item ID: " + request.getInventoryId()));

        Master master = masterRepository.findById(request.getMasterId())
                .orElseThrow(() -> new MasterNotFoundException("Invalid Master ID: " + request.getMasterId()));


        Long masterLedgerId = master.getLedger() != null ? master.getLedger().getLedgerId() : null;

        long ledgerId = request.getLedgerId();
        double quantity = request.getQuantity();

        if (masterLedgerId == null || masterLedgerId.longValue() != ledgerId) {
            throw new LedgerNotFoundException("Ledger does not match the master voucher.");
        }

        switch (master.getVoucherType()) {
            case SALES    -> processSalesItem(inventory, quantity);
            case PURCHASE -> processPurchaseItem(inventory, quantity);
            default       -> throw new VoucherNotFound("Unsupported voucher type: " + master.getVoucherType());
        }

        double unitPrice = inventory.getItemCost();
        double totalPrice = unitPrice * quantity;

        LineItems lineItem = new LineItems();
        lineItem.setInventory(inventory);
        lineItem.setMaster(master);
        lineItem.setItemName(inventory.getItemName());
        lineItem.setQuantity(quantity);
        lineItem.setUnitPrice(unitPrice);
        lineItem.setTotalPrice(totalPrice);
        lineItem.setVoucherType(master.getVoucherType());

        lineItemsRepository.save(lineItem);

        // Update master amount
        updateMasterTotal(master);

        return lineItemsMapper.mapToLineItem(lineItem);
    }

    private void processSalesItem(Inventory inventory, double quantity) {
        if (inventory.getItemQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock for sale.");
        }
        inventory.setItemQuantity(inventory.getItemQuantity() - quantity);
        inventoryRepository.save(inventory);
    }

    private void processPurchaseItem(Inventory inventory, double quantity) {
        inventory.setItemQuantity(inventory.getItemQuantity() + quantity);
        inventoryRepository.save(inventory);
    }

    private void updateMasterTotal(Master master) {

        if (master.getVoucherType() != VoucherType.SALES) {
            throw new VoucherNotFound("Invalid Voucher Type ");
        }

        List<LineItems> lineItemsList = lineItemsRepository.findByMaster_MasterId(master.getMasterId());
        double totalAmount = lineItemsList.stream()
                .mapToDouble(LineItems::getTotalPrice)
                .sum();

        master.setAmount(totalAmount);
        masterRepository.save(master);
    }


    // Optional: Validate if needed in UI
    private void validateMasterTotalAmount(Master master) {
        double sumLineItems = master.getLineItems().stream()
                .mapToDouble(LineItems::getTotalPrice)
                .sum();

        if (master.getAmount() != sumLineItems) {
            throw new IllegalArgumentException("Master total does not match the sum of line items.");
        }
    }

//    @Override
//    @Transactional
//    public LineItemsResponse updateLineItem(long lineItemId, double newQuantity, long ledgerId) {
//        LineItems lineItem = lineItemsRepository.findById(lineItemId)
//                .orElseThrow(() -> new InvoiceNotFoundException("Line item not found with ID: " + lineItemId));
//
//        Master master = lineItem.getMaster();
//        Inventory inventory = lineItem.getInventory();
//
//        Long masterLedgerId = master.getLedger() != null ? master.getLedger().getLedgerId() : null;
//        if (masterLedgerId == null || masterLedgerId != ledgerId) {
//            throw new LedgerNotFoundException("Ledger does not match the master voucher.");
//        }
//
//        double oldQuantity = lineItem.getQuantity();
//        double quantityDiff = newQuantity - oldQuantity;
//
//        // Adjust inventory based on voucher type
//        switch (master.getVoucherType()) {
//            case SALES -> {
//                double newStock = inventory.getItemQuantity() - quantityDiff;
//                if (newStock < 0) {
//                    throw new InsufficientStockException("Not enough stock for update.");
//                }
//                inventory.setItemQuantity(newStock);
//            }
//            case PURCHASE -> {
//                inventory.setItemQuantity(inventory.getItemQuantity() + quantityDiff);
//            }
//            default -> throw new VoucherNotFound("Unsupported voucher type: " + master.getVoucherType());
//        }
//
//        inventoryRepository.save(inventory);
//
//        // Update line item values
//        lineItem.setQuantity(newQuantity);
//        double newTotal = newQuantity * lineItem.getUnitPrice();
//        lineItem.setTotalPrice(newTotal);
//        lineItemsRepository.save(lineItem);
//
//        updateMasterTotal(master);
//
//        return lineItemsMapper.mapToLineItem(lineItem);
//    } code was wrong have to some change


//    @Override
//    @Transactional
//    public void deleteLineItem(long lineItemId, long ledgerId) {
//        LineItems lineItem = lineItemsRepository.findById(lineItemId)
//                .orElseThrow(() -> new InvoiceNotFoundException("Line item not found with ID: " + lineItemId));
//
//        Master master = lineItem.getMaster();
//        Inventory inventory = lineItem.getInventory();
//
//        Long masterLedgerId = master.getLedger() != null ? master.getLedger().getLedgerId() : null;
//        if (masterLedgerId == null || masterLedgerId != ledgerId) {
//            throw new LedgerNotFoundException("Ledger does not match the master voucher.");
//        }
//
//        double quantity = lineItem.getQuantity();
//
//        // Restore inventory
//        switch (master.getVoucherType()) {
//            case SALES -> inventory.setItemQuantity(inventory.getItemQuantity() + quantity);
//            case PURCHASE -> {
//                double newStock = inventory.getItemQuantity() - quantity;
//                if (newStock < 0) {
//                    throw new InsufficientStockException("Cannot remove purchase. Inventory would go negative.");
//                }
//                inventory.setItemQuantity(newStock);
//            }
//            default -> throw new VoucherNotFound("Unsupported voucher type: " + master.getVoucherType());
//        }
//
//        inventoryRepository.save(inventory);
//        lineItemsRepository.delete(lineItem);
//
//        updateMasterTotal(master);
//    }


}
