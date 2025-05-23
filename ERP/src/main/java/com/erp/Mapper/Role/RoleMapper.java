package com.erp.Mapper;

import com.erp.Dto.Request.RoleListRequest;
import com.erp.Dto.Request.RoleRequest;
import com.erp.Dto.Response.RoleResponse;
import com.erp.Model.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

     Set<Role> mapToSetOfRole(Set<RoleRequest> roleRequests);

    Set<RoleResponse> mapToListOfRoleRequest(Set<Role> roles);
}
