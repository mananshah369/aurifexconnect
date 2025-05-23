package com.erp.Service.Role;

import com.erp.Dto.Request.RoleRequest;
import com.erp.Dto.Response.RoleResponse;
import com.erp.Mapper.Role.RoleMapper;
import com.erp.Model.Role;
import com.erp.Repository.Role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleServices{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Set<RoleResponse> createListOfRoles(Set<RoleRequest> roleSetRequest) {
        Set<Role> roles = roleMapper.mapToSetOfRole(roleSetRequest);
        roleRepository.saveAll(roles);
        return roleMapper.mapToListOfRoleRequest(roles);
    }
}
