package com.erp.Service.InvoiceLineItemService;

import com.erp.Dto.Response.InvoiceLineItemsResponse;
import com.erp.Exception.Ledger.LedgerNotFoundException;
import com.erp.Exception.Inventory_Exception.InventoryNotFoundException;
import com.erp.Exception.Inventory_Exception.WrongQuantityNumberException;
import com.erp.Exception.Invoice_Exception.InsufficientStockException;
import com.erp.Exception.Invoice_Exception.InvoiceNotFoundException;
import com.erp.Mapper.InvoiceLineItems.InvoiceLineItemsMapper;
import com.erp.Model.Inventory;
import com.erp.Model.Invoice;
import com.erp.Model.InvoiceLineItems;
import com.erp.Repository.Inventory.InventoryRepository;
import com.erp.Repository.Invoice.InvoiceRepository;
import com.erp.Repository.InvoiceLineItems.InvoiceLineItemsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceLineItemServiceImpl implements InvoiceLineItemService{

    private final InvoiceLineItemsRepository repository;
    private final InvoiceRepository invoiceRepository;
    private final InventoryRepository inventoryRepository;
    private final InvoiceLineItemsMapper invoiceLineItemsMapper;

    @Override
    @Transactional(rollbackFor = { LedgerNotFoundException.class, InvoiceNotFoundException.class, InsufficientStockException.class })
    public InvoiceLineItemsResponse createInvoiceLineItems(long inventoryId, long invoiceId, long ledgerId , double quantity) {

        if (quantity <= 0 || quantity % 1 != 0) {
            throw new WrongQuantityNumberException("Quantity must be a positive whole number (e.g., 1, 2, 5)");
        }

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found , Invalid Invoice Id"));


        Long invoiceCustomerId = invoice.getLedger().getLedgerId();
        if (invoiceCustomerId == null || invoiceCustomerId.longValue() != ledgerId) {
            throw new LedgerNotFoundException("This invoice does not belong to the customer.");
        }

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));

        if (inventory.getItemQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient Stock . Available: " + inventory.getItemQuantity());
        }

        double unitPrice = inventory.getItemCost();
        double totalPrice = unitPrice * quantity;

        InvoiceLineItems lineItem = new InvoiceLineItems();
        lineItem.setDate(LocalDate.now());
        lineItem.setItemName(inventory.getItemName());
        lineItem.setInvoice(invoice);
        lineItem.setInventory(inventory);
        lineItem.setQuantity(quantity);
        lineItem.setUnitPrice(unitPrice);
        lineItem.setTotalPrice(totalPrice);

        inventory.setItemQuantity(inventory.getItemQuantity() - quantity);
        inventoryRepository.save(inventory);

        repository.save(lineItem);

        List<InvoiceLineItems> items = repository.findByInvoice_invoiceId(invoiceId);

        double updatedTotal = 0;
        for (InvoiceLineItems item : items) {
            updatedTotal += item.getTotalPrice();
        }

        invoice.setTotalAmount(updatedTotal);
        invoiceRepository.save(invoice);

        return invoiceLineItemsMapper.mapToLineItemsResponse(lineItem);
    }

    @Override
    public List<InvoiceLineItemsResponse> findById(long invoiceId) {
        List<InvoiceLineItems> invoiceLineItems = repository.findByInvoice_invoiceId(invoiceId);

        if (invoiceLineItems == null || invoiceLineItems.isEmpty()) {
            throw new InvoiceNotFoundException("No line items found for invoice ID: " + invoiceId);
        }

        return invoiceLineItemsMapper.mapToLineItemsResponse(invoiceLineItems);
    }

    @Override
    @Transactional
    public InvoiceLineItemsResponse updateInvoiceItems(long inventoryId, long invoiceId, double quantity) {
        if (quantity <= 0 || quantity % 1 != 0) {
            throw new WrongQuantityNumberException("Quantity must be a positive whole number.");
        }

        List<InvoiceLineItems> invoiceLineItems = repository.findByInvoice_invoiceId(invoiceId);
        if (invoiceLineItems == null || invoiceLineItems.isEmpty()) {
            throw new InvoiceNotFoundException("No line items found for invoice ID: " + invoiceId);
        }

        InvoiceLineItems targetItem = invoiceLineItems.stream()
                .filter(item -> item.getInventory().getItemId() == inventoryId)
                .findFirst()
                .orElseThrow(() -> new InventoryNotFoundException("No matching line item found for inventory ID: " + inventoryId));

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + inventoryId));


        double oldQuantity = targetItem.getQuantity();
        double restoredStock = inventory.getItemQuantity() + oldQuantity;

        if (restoredStock < quantity) {
            throw new InsufficientStockException("Insufficient inventory. Available after restore: " + restoredStock);
        }


        inventory.setItemQuantity(restoredStock - quantity);
        inventoryRepository.save(inventory);

        targetItem.setQuantity(quantity);
        double totalPrice = targetItem.getUnitPrice() * quantity;
        targetItem.setTotalPrice(totalPrice);
        targetItem.setDate(LocalDate.now());
        repository.save(targetItem);


        double updatedInvoiceTotal = invoiceLineItems.stream()
                .mapToDouble(InvoiceLineItems::getTotalPrice)
                .sum();

        Invoice invoice = targetItem.getInvoice();
        invoice.setTotalAmount(updatedInvoiceTotal);
        invoiceRepository.save(invoice);

        return invoiceLineItemsMapper.mapToLineItemsResponse(targetItem);

    }

    @Override
    @Transactional
    public InvoiceLineItemsResponse deleteInvoiceItems(long invoiceId, long inventoryId) {

        List<InvoiceLineItems> invoiceLineItems = repository.findByInvoice_invoiceId(invoiceId);
        if (invoiceLineItems == null || invoiceLineItems.isEmpty()) {
            throw new InvoiceNotFoundException("No line items found for invoice ID: " + invoiceId);
        }

        InvoiceLineItems targetItem = invoiceLineItems.stream()
                .filter(item -> item.getInventory().getItemId() == inventoryId)
                .findFirst()
                .orElseThrow(() -> new InventoryNotFoundException("No matching line item found for inventory ID: " + inventoryId));

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + inventoryId));

        // Restore inventory stock
        inventory.setItemQuantity(inventory.getItemQuantity() + targetItem.getQuantity());
        inventoryRepository.save(inventory);

        // Save the item to return as response before deleting
        InvoiceLineItemsResponse response = invoiceLineItemsMapper.mapToLineItemsResponse(targetItem);

        // Delete the line item
        repository.delete(targetItem);

        // Recalculate the total for the invoice
        double updatedTotal = repository.findByInvoice_invoiceId(invoiceId).stream()
                .mapToDouble(InvoiceLineItems::getTotalPrice)
                .sum();

        Invoice invoice = targetItem.getInvoice();
        invoice.setTotalAmount(updatedTotal);
        invoiceRepository.save(invoice);

        return response;
    }


}
