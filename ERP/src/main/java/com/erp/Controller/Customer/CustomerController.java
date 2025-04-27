package com.erp.Controller.Customer;

import com.erp.Dto.Request.CustomerRequest;
import com.erp.Dto.Response.CustomerResponse;
import com.erp.Service.CustomerService.CustomerService;
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
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("customer")
    @Operation( summary = "Create a new customer",
            description = "API to create a new customer record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    public ResponseEntity<ResponseStructure<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseBuilder.success(HttpStatus.CREATED,"Customer Created",customerResponse);
    }

    @Operation(
            summary = "Update customer information",
            description = "API to update existing customer information by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @PutMapping("customer/{id}")
    public ResponseEntity<ResponseStructure<CustomerResponse>> updateCustomerInfo(@RequestBody CustomerRequest customerRequest, @PathVariable long id){
        CustomerResponse customerResponse = customerService.updateCustomerInfo(customerRequest, id);
        return ResponseBuilder.success(HttpStatus.OK,"Customer Info Updated Successfully", customerResponse);
    }

    @Operation(
            summary = "Find customer by ID",
            description = "API to fetch customer details by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer found successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @GetMapping("customer/{customerId}")
    public ResponseEntity<ResponseStructure<CustomerResponse>> findByCustomerId(@PathVariable long customerId){
        CustomerResponse customerResponse = customerService.findByCustomerId(customerId);
        return ResponseBuilder.success(HttpStatus.OK,"Customer Found Successfully!", customerResponse);
    }

    @Operation(
            summary = "Delete customer by ID",
            description = "API to delete a customer record by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            }
    )
    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<ResponseStructure<CustomerResponse>> deleteByCustomerId(@PathVariable long customerId){
        CustomerResponse customerResponse = customerService.deleteByCustomerId(customerId);
        return ResponseBuilder.success(HttpStatus.OK,"Customer Deleted Successfully!!",customerResponse);
    }

    @Operation(
            summary = "Get all customers",
            description = "API to fetch all customers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All customers fetched successfully")
            }
    )
    @GetMapping("customers")
    public ResponseEntity<ListResponseStructure<CustomerResponse>> getAllCustomers(){
        List<CustomerResponse> allCustomers = customerService.getAllCustomer();
        return ResponseBuilder.success(HttpStatus.OK,"All Customers Fetched Successfully", allCustomers);
    }
}
