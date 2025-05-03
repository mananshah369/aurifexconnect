package com.erp.Service.BillLine;

import com.erp.Dto.Request.BillLineRequest;
import com.erp.Dto.Response.BillLineResponse;
import com.erp.Exception.BillLine.BillLineNotFoundException;
import com.erp.Exception.Bills.BillNotFoundException;
import com.erp.Exception.Inventory_Exception.InventoryNotFoundException;
import com.erp.Mapper.BillLines.BillLinesMapper;
import com.erp.Model.BillLine;
import com.erp.Model.Bills;
import com.erp.Model.Inventory;
import com.erp.Repository.BillLine.BillLineRepository;
import com.erp.Repository.Bills.BillsRepository;
import com.erp.Repository.Inventory.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillLineServiceImpl implements BillLineService {

    private final BillsRepository billsRepository;
    private final InventoryRepository inventoryRepository;
    private final BillLineRepository billLineRepository;
    private final BillLinesMapper billLinesMapper;

    @Override
    public BillLineResponse addBillLineItems(BillLineRequest billLineRequest, long billId, long itemId) {

        Bills bill = billsRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Invalid billId!, Bill not found !"));

        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Invalid itemId!, Inventory not found !"));

        BillLine billLine = billLinesMapper.mapTOBillLine(billLineRequest);

        // calculate total amount
        double total = billLine.getQuantity() * billLine.getUnitPrice();
        billLine.setTotalAmount(total);

        // update inventory quantity
        double newQuantity = inventory.getItemQuantity() + billLine.getQuantity();
        inventory.setItemQuantity(newQuantity);

        billLine.setInventory(inventory);
        billLine.setBill(bill);

        billLineRepository.save(billLine);

        return billLinesMapper.mapToBillLineResponse(billLine);

    }

    @Override
    public BillLineResponse updateBillLineItemById(BillLineRequest billLineRequest, long billLineId, long billId, long itemId) {

        BillLine existingBillLine = billLineRepository.findById(billLineId)
                .orElseThrow(() -> new BillLineNotFoundException("Invalid billLineId!, Bill Line not found!"));

        Bills bill = billsRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Invalid billId!, Bill not found!"));

        Inventory inventory = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new InventoryNotFoundException("Invalid itemId!, Inventory not found!"));

        // Adjust inventory quantity
        double oldQty = existingBillLine.getQuantity();
        double newQty = billLineRequest.getQuantity();
        double quantityDifference = newQty - oldQty;

        double updatedInventoryQty = inventory.getItemQuantity() + quantityDifference;
        inventory.setItemQuantity(updatedInventoryQty);

        billLinesMapper.mapToBillLineEntity(billLineRequest, existingBillLine);

        // Recalculate total
        double total = existingBillLine.getQuantity() * existingBillLine.getUnitPrice();
        existingBillLine.setTotalAmount(total);

        existingBillLine.setInventory(inventory);
        existingBillLine.setBill(bill);

        billLineRepository.save(existingBillLine);

        return billLinesMapper.mapToBillLineResponse(existingBillLine);
    }

    @Override
    public List<BillLineResponse> getBillLinesByDescription(String description) {

        List<BillLine> billLines = billLineRepository.findByDescriptionContainingIgnoreCase(description);

        return billLinesMapper.mapToBillLineResponseList(billLines);
    }

    @Override
    public BillLineResponse deleteBillLineById(long billLineId) {
        BillLine billLine = billLineRepository.findById(billLineId)
                .orElseThrow(() -> new BillLineNotFoundException("BillLine not found with ID: " + billLineId));

        // Re adjust inventory back
        Inventory inventory = billLine.getInventory();
        inventory.setItemQuantity(inventory.getItemQuantity() - billLine.getQuantity());

        billLineRepository.deleteById(billLineId);

        return billLinesMapper.mapToBillLineResponse(billLine);
    }

    @Override
    public BillLineResponse getBillLineById(long billLineId) {
        BillLine billLine = billLineRepository.findById(billLineId)
                .orElseThrow(() -> new BillLineNotFoundException("BillLine not found with ID: " + billLineId));
        return billLinesMapper.mapToBillLineResponse(billLine);
    }


    private static void validateBillTotalAmount(Bills bill) {
        List<BillLine> billLines = bill.getBillLines();

        double billLineTotalAmount = 0;

        for(BillLine line : billLines) {
            billLineTotalAmount = billLineTotalAmount + line.getTotalAmount();
        }
        if (bill.getTotalAmount() == billLineTotalAmount) {
            throw new IllegalArgumentException("Bill total does not match sum of line items!");
        }
    }
}
