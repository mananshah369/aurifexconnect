package com.erp.Controller.Bills;

import com.erp.Dto.Request.BillsRequest;
import com.erp.Dto.Response.BillsResponse;
import com.erp.Service.Bills.BillsService;
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
@RequestMapping("/bills")
public class BillsController {

    private final BillsService billsService;

    @PostMapping
    public ResponseEntity<ResponseStructure<BillsResponse>> addBill(@RequestBody BillsRequest billsRequest,@RequestParam long ledgerId) {
        BillsResponse response = billsService.addBill(billsRequest, ledgerId);
      return  ResponseBuilder.success(HttpStatus.CREATED,"Bill added successfully !!", response);
    }

    @PutMapping("/{billId}")
    public ResponseEntity<ResponseStructure<BillsResponse>> updateBill(@RequestBody BillsRequest billsRequest ,@PathVariable long billId,@RequestParam long ledgerId){
        BillsResponse response = billsService.updateBill(billsRequest,billId, ledgerId);
        return ResponseBuilder.success(HttpStatus.OK,"Bill Updated successfully !!",response);

    }

    @GetMapping("/{billId}")
    public ResponseEntity<ResponseStructure<BillsResponse>> findByBillId(@PathVariable long billId){
        BillsResponse response = billsService.findByBillId(billId);
        return ResponseBuilder.success(HttpStatus.OK,"Bill found Successfully!!",response);
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<ResponseStructure<BillsResponse>> deleteByBillId(@PathVariable long billId) {
        BillsResponse response = billsService.deleteByBillId(billId);
        return ResponseBuilder.success(HttpStatus.OK,"Bill deleted successfully!!",response);

    }

    @GetMapping
    public ResponseEntity<ListResponseStructure<BillsResponse>> findBillBySupplierName(@RequestParam String legerName){
        List<BillsResponse> responses = billsService.findBillBySupplierName(legerName);
        return ResponseBuilder.success(HttpStatus.OK,"Bills found successfully !!",responses);
    }
}
