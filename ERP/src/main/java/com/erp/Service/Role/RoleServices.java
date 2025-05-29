package com.erp.Service.Role;

import com.erp.Dto.Request.RoleListRequest;
import com.erp.Dto.Response.RoleResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface RoleServices {


    List<RoleResponse> createRoles(@Valid RoleListRequest roleSetRequest);


    List<RoleResponse> getAllRoles();
}
