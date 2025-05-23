package com.example.dio.dto.request;

import com.example.dio.enums.UserRole;

public record AuthRecord(
        long userId,
        String userName,
        String email,
        UserRole role,
        long accessExpiration,
        long refreshExpiration
) {
}
