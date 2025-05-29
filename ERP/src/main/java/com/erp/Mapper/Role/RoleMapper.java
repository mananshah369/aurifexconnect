package com.erp.Mapper.Role;

import com.erp.Dto.Request.RoleListRequest;
import com.erp.Dto.Request.RoleRequest;
import com.erp.Dto.Response.RoleResponse;
import com.erp.Model.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    Role toRole(RoleRequest roleRequest);


    RoleResponse toRoleResponse(Role role);

    List<RoleResponse> toRoleResponseList(List<Role> roles);

    default List<Role> toRoleList(Set<RoleRequest> roleRequests) {
        return roleRequests.stream()
                .map(this::toRole)
                .toList();
    }



}
