package com.erp.Controller.ServiceType;

import com.erp.Dto.Request.ServiceTypeRequest;
import com.erp.Dto.Response.ServiceTypeResponse;
import com.erp.Service.ServiceType.ServiceTypeService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import com.erp.Utility.SimpleErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/service")
public class ServicesController {

    private final ServiceTypeService serviceTypeService;

    @PostMapping
    @Operation(
            summary = "Add a new service",
            description = "This API endpoint allows you to add a new service.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Service added successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    public ResponseEntity<ResponseStructure<ServiceTypeResponse>> addService(@RequestBody ServiceTypeRequest serviceTypeRequest){
        ServiceTypeResponse servicesResponse = serviceTypeService.addService(serviceTypeRequest);
        return ResponseBuilder.success(HttpStatus.CREATED,"Service added successfully !!",servicesResponse);
    }

    @PutMapping("/{serviceId}")
    @Operation(
            summary = "Update an existing service",
            description = "This API endpoint allows you to update an existing service by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
                    @ApiResponse(responseCode = "404", description = "Service not found")
            }
    )
    public ResponseEntity<ResponseStructure<ServiceTypeResponse>> updateServices(@RequestBody ServiceTypeRequest serviceTypeRequest,@PathVariable long serviceId){
        ServiceTypeResponse servicesResponse = serviceTypeService.updateById(serviceTypeRequest,serviceId);
        return ResponseBuilder.success(HttpStatus.OK,"Service updated successfully !!",servicesResponse);
    }

    @GetMapping
    @Operation(
            summary = "Find services by name",
            description = "This API endpoint allows you to search for services by name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Services found successfully"),
                    @ApiResponse(responseCode = "404", description = "No services found")
            }
    )
    public ResponseEntity<ListResponseStructure<ServiceTypeResponse>> findByName(@RequestParam(name = "name") String name){
        List<ServiceTypeResponse> servicesResponse = serviceTypeService.findByName(name);
        return ResponseBuilder.success(HttpStatus.OK,"Service updated successfully !!",servicesResponse);
    }

    @GetMapping("/{serviceId}")
    @Operation(
            summary = "Find service by ID",
            description = "This API endpoint allows you to retrieve a service by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service found successfully"),
                    @ApiResponse(responseCode = "404", description = "Service not found")
            }
    )
    public ResponseEntity<ResponseStructure<ServiceTypeResponse>> findByServiceId(@PathVariable long serviceId){
        ServiceTypeResponse servicesResponse = serviceTypeService.findByServiceId(serviceId);
        return ResponseBuilder.success(HttpStatus.OK,"Service found Successfully!!",servicesResponse);
    }


    @DeleteMapping("{serviceId}")
    @Operation(
            summary = "Delete a service by ID",
            description = "This API endpoint allows you to delete a service by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Service not found", content = @Content(schema = @Schema(implementation = SimpleErrorResponse.class)))
            }
    )
    public ResponseEntity<ResponseStructure<ServiceTypeResponse>> deleteByItemId(@PathVariable long serviceId) {
        ServiceTypeResponse response = serviceTypeService.deleteByItemId(serviceId);
        return ResponseBuilder.success(HttpStatus.OK,"Service deleted successfully!!",response);

    }

}