package com.erp.Controller.Ledger;

import com.erp.Dto.Request.LedgerRequest;
import com.erp.Dto.Response.LedgerResponse;
import com.erp.Service.LedgerService.LedgerService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class LedgerController {

    private final LedgerService ledgerService;

    @PostMapping("ledger")
    @Operation( summary = "Create a new Ledger",
            description = "API to create a new Ledger record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ledger created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    public ResponseEntity<ResponseStructure<LedgerResponse>> createLedger(@Valid @RequestBody LedgerRequest ledgerRequest){
        LedgerResponse ledgerResponse = ledgerService.createLedger(ledgerRequest);
        return ResponseBuilder.success(HttpStatus.CREATED,"Ledger Created", ledgerResponse);
    }

    @Operation(
            summary = "Update Ledger information",
            description = "API to update existing Ledger information by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ledger updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Ledger not found")
            }
    )
    @PutMapping("ledger/{id}")
    public ResponseEntity<ResponseStructure<LedgerResponse>> updateLedgerInfo(@RequestBody LedgerRequest ledgerRequest, @PathVariable long id){
        LedgerResponse ledgerResponse = ledgerService.updateLedgerInfo(ledgerRequest, id);
        return ResponseBuilder.success(HttpStatus.OK,"Ledger Info Updated Successfully", ledgerResponse);
    }

    @Operation(
            summary = "Find Ledger by ID",
            description = "API to fetch Ledger details by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ledger found successfully"),
                    @ApiResponse(responseCode = "404", description = "Ledger not found")
            }
    )
    @GetMapping("ledger/{ledgerId}")
    public ResponseEntity<ResponseStructure<LedgerResponse>> findByLedgerId(@PathVariable long ledgerId){
        LedgerResponse ledgerResponse = ledgerService.findByLedgerId(ledgerId);
        return ResponseBuilder.success(HttpStatus.OK,"Ledger Found Successfully!", ledgerResponse);
    }

    @Operation(
            summary = "Delete Ledger by ID",
            description = "API to delete a Ledger record by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ledger deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Ledger not found")
            }
    )
    @DeleteMapping("ledger/{ledgerId}")
    public ResponseEntity<ResponseStructure<LedgerResponse>> deleteByLedgerId(@PathVariable long ledgerId){
        LedgerResponse ledgerResponse = ledgerService.deleteByLedgerId(ledgerId);
        return ResponseBuilder.success(HttpStatus.OK,"Ledger Deleted Successfully!!", ledgerResponse);
    }

    @Operation(
            summary = "Get all Ledger",
            description = "API to fetch all customers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All Ledger fetched successfully")
            }
    )
    @GetMapping("ledger")
    public ResponseEntity<ListResponseStructure<LedgerResponse>> getAllLedger(){
        List<LedgerResponse> allLedger = ledgerService.getAllLedger();
        return ResponseBuilder.success(HttpStatus.OK,"All Ledger Fetched Successfully", allLedger);
    }

    @Operation(
            summary = "Get all Ledger",
            description = "API to fetch all customers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All Ledger fetched successfully")
            }
    )
    @GetMapping("ledger/by-Name/{ledgerName}")
    public ResponseEntity<ListResponseStructure<LedgerResponse>> getLedgerByName(@PathVariable String ledgerName){
        List<LedgerResponse> ledgerResponses = ledgerService.getLedgerByName(ledgerName);
        return ResponseBuilder.success(HttpStatus.OK,"All Ledger Fetched By NameSuccessfully", ledgerResponses);
    }
}
