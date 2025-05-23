package com.erp.Service.Role;

import com.erp.Dto.Request.RoleRequest;
import com.erp.Dto.Response.RoleResponse;

import java.util.Set;

public interface RoleServices {

    Set<RoleResponse> createListOfRoles(Set<RoleRequest> roleSetRequest);
}
