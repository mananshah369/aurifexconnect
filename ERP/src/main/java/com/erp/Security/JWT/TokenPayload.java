package com.erp.Security.JWT;

import java.time.Instant;

public record TokenPaylod(
        Map<String, Object
        Instant issuedAt,
        Instant expiration
) {
}
