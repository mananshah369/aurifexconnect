package com.erp.Dto.Request;

public record RootAuthRecord(
                             long id,
                             String email,
                             long accessExpiration,
                             long refreshExpiration
) {
}
