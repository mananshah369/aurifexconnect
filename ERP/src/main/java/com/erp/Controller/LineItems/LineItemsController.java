package com.erp.Controller.LineItems;

import com.erp.Dto.Request.LedgerRequest;
import com.erp.Dto.Response.LedgerResponse;
import com.erp.Dto.Response.LineItemsResponse;
import com.erp.Service.LineItems.LineItemService;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class LineItemsController {

    private final LineItemService lineItemService;

    @PostMapping("items")
    public ResponseEntity<ResponseStructure<LineItemsResponse>> createLineItems(@RequestParam long inventoryId,@RequestParam long masterId,@RequestParam long ledgerId,@RequestParam double quantity){
        LineItemsResponse lineItemsResponse = lineItemService.createLineItems(inventoryId,masterId,ledgerId,quantity);
        return ResponseBuilder.success(HttpStatus.CREATED,"Customer LineItems Successfully", lineItemsResponse);
    }
}
