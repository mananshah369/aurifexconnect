package com.erp.Utility;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleResponseStructure {
    private int status;
    private String message;
}
