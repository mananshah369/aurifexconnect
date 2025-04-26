package com.erp.Utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@AllArgsConstructor
public class SimpleErrorResponse {
    private String type;
    private int status; // 404
    private String message; // failed to update the user, The user is not found by the given Id
}
