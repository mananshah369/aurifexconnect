package com.erp.Controller.InvoiceLineItem;

import com.erp.Dto.Response.InvoiceLineItemsResponse;
import com.erp.Service.InvoiceLineItemService.InvoiceLineItemService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class InvoiceLineItemController {

    private final InvoiceLineItemService invoiceLineItemService;

    @PostMapping("create")
    public ResponseEntity<ResponseStructure<InvoiceLineItemsResponse>> createInvoiceLineItems(@RequestParam long inventoryId, @RequestParam long invoiceId, @RequestParam long ledgerId, @RequestParam double quantity) {
        InvoiceLineItemsResponse invoiceLineItemsResponse = invoiceLineItemService.createInvoiceLineItems(inventoryId, invoiceId, ledgerId, quantity);
        return ResponseBuilder.success(HttpStatus.CREATED, "Item Added Successfully", invoiceLineItemsResponse);
    }

    @GetMapping("invoiceLineItems/{invoiceId}")
    public ResponseEntity<ListResponseStructure<InvoiceLineItemsResponse>> findByInvoiceId(@PathVariable long invoiceId) {
        List<InvoiceLineItemsResponse> invoiceLineItemsResponses = invoiceLineItemService.findById(invoiceId);
        return ResponseBuilder.success(HttpStatus.OK, "Items Found Successfully", invoiceLineItemsResponses);
    }

    @PutMapping("invoiceItems")
    public ResponseEntity<ResponseStructure<InvoiceLineItemsResponse>> updateByInvoiceId(@RequestParam long inventoryId, @RequestParam long invoiceId, @RequestParam double quantity) {
        InvoiceLineItemsResponse invoiceLineItemsResponse = invoiceLineItemService.updateInvoiceItems(inventoryId,invoiceId,quantity);
        return ResponseBuilder.success(HttpStatus.OK,"Invoice Item Updated Successfully",invoiceLineItemsResponse);
    }

    @DeleteMapping("invoiceItems/delete")
    public ResponseEntity<ResponseStructure<InvoiceLineItemsResponse>> deleteByInvoiceId(@RequestParam long invoiceId, @RequestParam long inventoryId){
        InvoiceLineItemsResponse invoiceLineItemsResponse = invoiceLineItemService.deleteInvoiceItems(invoiceId,inventoryId);
        return ResponseBuilder.success(HttpStatus.OK,"Invoice Item Deleted Successfully",invoiceLineItemsResponse);
    }
}
