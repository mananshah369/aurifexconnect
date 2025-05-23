package com.erp.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleRequest {

    @NotBlank
    private String roleName;

    //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleRequest)) return false;
        RoleRequest that = (RoleRequest) o;
        return roleName != null && roleName.equalsIgnoreCase(that.roleName);
    }

    @Override
    public int hashCode() {
        return roleName == null ? 0 : roleName.toLowerCase().hashCode();
    }
}
