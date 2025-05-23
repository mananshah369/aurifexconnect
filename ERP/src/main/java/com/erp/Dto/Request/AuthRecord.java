package com.erp.Dto.Request;

import com.erp.Enum.UserType;


import java.util.Set;

public record AuthRecord(
        long id,
        String email,
        UserType userType,
        boolean isActive,
        long accessExpiration,
        long refreshExpiration
) {
}
