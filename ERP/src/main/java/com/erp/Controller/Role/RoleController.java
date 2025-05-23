package com.erp.Controller.Role;

import com.erp.Dto.Request.RoleListRequest;
import com.erp.Dto.Request.RoleRequest;
import com.erp.Dto.Response.RoleResponse;
import com.erp.Service.Role.RoleServices;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import com.erp.Utility.SetResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class RoleController {

    private final RoleServices roleServices;

    @PostMapping("/user/roles")
    public ResponseEntity<SetResponseStructure<RoleResponse>> createListOfRoles (@RequestBody @Valid Set<RoleRequest> roleSetRequest){
        Set<RoleResponse> roleResponses = roleServices.createListOfRoles(roleSetRequest);
        return ResponseBuilder.success(HttpStatus.CREATED,"Created",roleResponses);
    }
}
