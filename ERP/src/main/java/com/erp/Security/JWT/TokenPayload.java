package com.erp.Security.JWT;

import java.time.Instant;
import java.util.Map;

public record TokenPayload(
        Map<String, Object> claims,
        Instant issuedAt,
        Instant expiration
) {
}