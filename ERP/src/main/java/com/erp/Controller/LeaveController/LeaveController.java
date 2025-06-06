package com.erp.Controller.LeaveController;

import com.erp.Dto.Request.Param;
import com.erp.Dto.Request.LeaveRequest;
import com.erp.Dto.Response.LeaveResponse;
import com.erp.Service.LeaveService.LeaveService;
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
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/apply")
    @Operation(summary = "Apply Leave",
            description = "API Endpoint to apply for a leave request",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Leave request submitted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<LeaveResponse>> applyLeave(@Valid @RequestBody LeaveRequest request) {
        LeaveResponse response = leaveService.createLeaveRequest(request);
        return ResponseBuilder.success(HttpStatus.CREATED, "Leave request submitted", response);
    }

    @PutMapping("/approve")
    @Operation(summary = "Approve Leave",
            description = "API Endpoint to approve a leave request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Leave approved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<LeaveResponse>> approveLeave(@Valid @RequestBody LeaveRequest request) {
        LeaveResponse response = leaveService.updateLeaveStatus(request);
        return ResponseBuilder.success(HttpStatus.OK, "Leave approved", response);
    }

    @PutMapping("/reject")
    @Operation(summary = "Reject Leave",
            description = "API Endpoint to reject a leave request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Leave request rejected successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<LeaveResponse>> rejectLeave(@Valid @RequestBody LeaveRequest request) {
        LeaveResponse response = leaveService.updateLeaveStatus(request);
        return ResponseBuilder.success(HttpStatus.OK, "Leave rejected", response);
    }

    @PostMapping("/getbyuserid")
    @Operation(summary = "Get Leave Requests by User ID",
            description = "API Endpoint to find leave requests by user ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found leave requests by user"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<LeaveResponse>> getLeaveByUserId(@Valid @RequestBody Param param) {
        LeaveResponse response = leaveService.getLeaveRequestsByUserId(param);
        return ResponseBuilder.success(HttpStatus.OK, "Leave request by user", response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Leave Requests",
            description = "API Endpoint to retrieve all leave requests",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All leave requests retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ListResponseStructure<LeaveResponse>> getAllLeaveRequests() {
        List<LeaveResponse> response = leaveService.getAllLeaveRequests();
        return ResponseBuilder.success(HttpStatus.OK, "All leave requests retrieved", response);
    }

    @PutMapping("/update")
    @Operation(summary = "Update Leave Request",
            description = "API Endpoint to update leave request by leave ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Leave request updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<LeaveResponse>> updateLeave(@Valid @RequestBody LeaveRequest request) {
        LeaveResponse response = leaveService.updateLeaveRequestByLeaveId(request);
        return ResponseBuilder.success(HttpStatus.OK, "Leave request updated", response);
    }

    @PostMapping("/status")
    @Operation(summary = "Get Leave Requests by Status",
            description = "API Endpoint to find leave requests filtered by status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found leave requests by status"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ListResponseStructure<LeaveResponse>> getLeavesByStatus(@Valid @RequestBody LeaveRequest request) {
        List<LeaveResponse> response = leaveService.getLeaveRequestsByStatus(request);
        return ResponseBuilder.success(HttpStatus.OK, "Leave requests by status", response);
    }

    @PostMapping("/date-range")
    @Operation(summary = "Get Leave Requests by Date Range",
            description = "API Endpoint to find leave requests within a date range",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found leave requests by date range"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ListResponseStructure<LeaveResponse>> getLeavesByDateRange(@Valid @RequestBody LeaveRequest request) {
        List<LeaveResponse> response = leaveService.getLeaveRequestsByDateRange(request);
        return ResponseBuilder.success(HttpStatus.OK, "Leave requests by date range", response);
    }

    @PostMapping("/delete")
    @Operation(summary = "Delete Leave Request",
            description = "API Endpoint to delete leave request by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Leave request deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ResponseStructure<LeaveResponse>> deleteLeave(@Valid @RequestBody Param param) {
        LeaveResponse response = leaveService.deleteLeaveRequest(param);
        return ResponseBuilder.success(HttpStatus.OK, "Leave deleted", response);
    }
}