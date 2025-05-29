package com.erp.Dto.Request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class RoleListRequest {


    @NotEmpty
    private Set<RoleRequest> roles;
}
