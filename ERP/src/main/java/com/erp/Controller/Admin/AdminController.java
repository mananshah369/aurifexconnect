package com.erp.Controller.Admin;

import com.erp.Dto.Request.AdminRequest;
import com.erp.Dto.Response.AdminResponse;
import com.erp.Service.Admin.AdminService;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.base-url}")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ROOT')")
    @PostMapping("/root/admins")
    public ResponseEntity<ResponseStructure<AdminResponse>> createAdmin(@RequestBody AdminRequest adminRequest){

        AdminResponse adminResponse = adminService.createAdmin(adminRequest);
        return ResponseBuilder.success(HttpStatus.CREATED,"Admin created successfully !!",adminResponse);

    }
 }
