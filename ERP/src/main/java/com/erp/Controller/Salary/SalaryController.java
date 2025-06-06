package com.erp.Controller.Salary;

import com.erp.Dto.Request.Param;
import com.erp.Dto.Request.SalaryRequest;
import com.erp.Dto.Response.SalaryResponse;
import com.erp.Service.SalaryService.SalaryService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("/user")
    @Operation(summary = "Find Salary records",
            description = "API Endpoint to Find Salary records",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salary records fetched"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ListResponseStructure<SalaryResponse>> getSalariesByUser(@RequestBody @Valid Param param) {
        List<SalaryResponse> responseList = salaryService.getSalariesByUserId(param);
        return ResponseBuilder.success(HttpStatus.OK, "Salary records fetched", responseList);
    }

    @PostMapping("/user/salary-month")
    @Operation(summary = "Find Salary records",
            description = "API Endpoint to Find Salary records",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salary records fetched"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<SalaryResponse>> getUserSalaryByMonth(@RequestBody @Valid SalaryRequest request) {
        SalaryResponse response = salaryService.getSalaryByUserAndMonth(request);
        return ResponseBuilder.success(HttpStatus.OK, "Salary record fetched", response);
    }

    @PutMapping("/pay")
    @Operation(summary = "Mark Salary As Paid",
            description = "API Endpoint to Update Salary records",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salary marked as paid successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<SalaryResponse>> markSalaryAsPaid(@RequestBody @Valid SalaryRequest request) {
        SalaryResponse response = salaryService.markSalaryAsPaid(request);
        return ResponseBuilder.success(HttpStatus.OK, "Salary marked as paid", response);
    }

    @PutMapping("/update")
    @Operation(summary = "Update Salary",
            description = "API Endpoint to Update Salary records",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salary records Updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<SalaryResponse>> updateSalary(@RequestBody @Valid SalaryRequest request) {
        SalaryResponse response = salaryService.updateSalary(request);
        return ResponseBuilder.success(HttpStatus.OK, "Salary updated successfully", response);
    }

    @GetMapping
    @Operation(summary = "Find All Salary records",
            description = "API Endpoint to Find All Salaries records",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salaries records fetched"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ListResponseStructure<SalaryResponse>> getAllSalaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<SalaryResponse> salaries = salaryService.getAllSalaries(page, size);
        return ResponseBuilder.success(HttpStatus.OK, "All salaries fetched", salaries);
    }

    @PostMapping("/month")
    @Operation(summary = "Find Salaries records for month",
            description = "API Endpoint to Find Salaries records by month",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salaries records fetched by month"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ListResponseStructure<SalaryResponse>> getSalariesByMonth(@RequestBody @Valid SalaryRequest request) {
        List<SalaryResponse> salaries = salaryService.getSalariesByMonth(request);
        return ResponseBuilder.success(HttpStatus.OK, "Salaries for month fetched", salaries);
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Delete Salary record By userId & Month",
            description = "API Endpoint to delete a salary record by userId and month",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Salary record deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    public ResponseEntity<ResponseStructure<SalaryResponse>> deleteSalaryByUserAndMonth(@RequestBody SalaryRequest request) {
        SalaryResponse response = salaryService.deleteSalaryByUserAndMonth(request);
        return ResponseBuilder.success(HttpStatus.OK, "Salary record deleted successfully!", response);
    }
}