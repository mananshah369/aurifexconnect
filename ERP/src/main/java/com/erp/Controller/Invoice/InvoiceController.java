package com.erp.Controller.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Dto.Response.InvoiceResponse;
import com.erp.Service.Invoice.InvoiceService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import com.erp.Utility.SimpleErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
@Tag(name = "Invoice Controller",description = "Collection of APIs Endpoints Dealing with Invoice Data")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("invoice")
    @Operation(description = """
            The API Endpoints to Created Invoice
            """,
            responses = {
                    @ApiResponse(responseCode = "201",description = "Created Successfully")
            })
    public ResponseEntity<ResponseStructure<InvoiceResponse>> createdInvoice(@Valid @RequestBody InvoiceRequest request , @RequestParam long customerId){
        InvoiceResponse invoiceResponse = invoiceService.create(request,customerId);
        return ResponseBuilder.success(HttpStatus.CREATED,"Invoice Created Successfully",invoiceResponse);
    }

    @GetMapping("invoice/{invoiceId}")
    @Operation(description = """
            The API Endpoints to Find Invoice By Id
            """,
            responses = {
                    @ApiResponse(responseCode = "200",description = "Found Successfully"),
                    @ApiResponse(responseCode = "404",description = "Invalid Invoice Id",content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<InvoiceResponse>> findById(@PathVariable long invoiceId){
        InvoiceResponse invoiceResponse = invoiceService.findById(invoiceId);
        return ResponseBuilder.success(HttpStatus.OK,"Invoice Found Successfully",invoiceResponse);
    }

    @GetMapping("invoice/customer")
    @Operation(description = """
            The API Endpoints to Find Invoice By Customer Name
            """,
            responses = {
                    @ApiResponse(responseCode = "200",description = "Found Successfully"),
                    @ApiResponse(responseCode = "404",description = "Invalid Customer Id",content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ListResponseStructure<InvoiceResponse>> findBycustomerName(@RequestParam String customerName){
        List<InvoiceResponse> invoiceResponse = invoiceService.findByCustomer_Name(customerName);
        return ResponseBuilder.success(HttpStatus.OK,"Invoice Found successfully",invoiceResponse);
    }


    @GetMapping("invoice")
    @Operation(description = """
            The API Endpoints to Find Invoice By Customer Name
            """,
            responses = {
                    @ApiResponse(responseCode = "200",description = "Found Successfully"),
                    @ApiResponse(responseCode = "404",description = "Invalid Customer Id",content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ListResponseStructure<InvoiceResponse>> findByAll(){
        List<InvoiceResponse> invoiceResponse = invoiceService.findByAllInvoice();
        return ResponseBuilder.success(HttpStatus.OK,"Invoice Found successfully",invoiceResponse);
    }

    @DeleteMapping("invoice/{invoiceId}")
    @Operation(description = """
            The API Endpoints to Delete Invoice By Id
            """,
            responses = {
                    @ApiResponse(responseCode = "200",description = "Deleted Successfully"),
                    @ApiResponse(responseCode = "404",description = "Invalid Invoice Id",content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<InvoiceResponse>> deleteInvoice(@PathVariable long invoiceId){
        InvoiceResponse invoiceResponse = invoiceService.delete(invoiceId);
        return ResponseBuilder.success(HttpStatus.OK,"Invoice Deleted successfully",invoiceResponse);
    }
}
