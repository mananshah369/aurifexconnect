package com.erp.Utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ResponseStructure<T> {
    private int status;
    private String message;
    private T data;
}

