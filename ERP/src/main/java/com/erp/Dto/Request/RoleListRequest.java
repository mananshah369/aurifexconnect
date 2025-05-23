package com.erp.Dto.Request;

import com.erp.Model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleListRequest {

    private Set<RoleRequest> roles;
}
