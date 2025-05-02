package com.erp.Controller.BillLine;

import com.erp.Dto.Request.BillLineRequest;
import com.erp.Dto.Response.BillLineResponse;
import com.erp.Service.BillLine.BillLineService;
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
@RequestMapping("/bill-lines")
public class BillLineController {

    private final BillLineService billLineService;

    @PostMapping
    public ResponseEntity<ResponseStructure<BillLineResponse>> addBillLineItems(
            @RequestBody BillLineRequest billLineRequest, @RequestParam long billId,@RequestParam long itemId){
        BillLineResponse lineResponse = billLineService.addBillLineItems(billLineRequest,billId,itemId);
        return ResponseBuilder.success(HttpStatus.CREATED,"BillLine added Successfully !!",lineResponse);
    }

    @PutMapping("/{billLineId}/update")
    public ResponseEntity<ResponseStructure<BillLineResponse>> updateBillLineItemById(
            @RequestBody BillLineRequest billLineRequest,@PathVariable long billLineId,@RequestParam long billId,@RequestParam long itemId){
        BillLineResponse lineResponse = billLineService.updateBillLineItemById(billLineRequest,billLineId,billId,itemId);
        return ResponseBuilder.success(HttpStatus.OK,"BillLine updated Successfully !!",lineResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ListResponseStructure<BillLineResponse>> getBillLinesByDescription( @RequestParam("description") String description){
        List<BillLineResponse> responses = billLineService.getBillLinesByDescription(description);
        return ResponseBuilder.success(HttpStatus.OK,"BillLineItems found Successfully !!",responses);
    }

    @DeleteMapping("/{billLineId}")
    public ResponseEntity<ResponseStructure<BillLineResponse>> deleteBillLineById(@PathVariable long billLineId) {
        BillLineResponse response = billLineService.deleteBillLineById(billLineId);
        return ResponseBuilder.success(HttpStatus.OK,"Bill deleted successfully!!",response);

    }

    @GetMapping("/{billLineId}")
    public ResponseEntity<ResponseStructure<BillLineResponse>> getBillLineById(@PathVariable long billLineId) {
        BillLineResponse response = billLineService.getBillLineById(billLineId);
        return ResponseBuilder.success(HttpStatus.OK, "BillLineItem found successfully!", response);
    }

}
