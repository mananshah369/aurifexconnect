package com.erp.Utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseBuilder {
    public static <T> ResponseEntity<ResponseStructure<T>> success(HttpStatus status, String message, T data) {
        ResponseStructure<T> structure = ResponseStructure.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(structure);
    }

    public static <T> ResponseEntity<ListResponseStructure<T>> success(HttpStatus status, String message, List<T> data) {
        ListResponseStructure<T> listResponseStructure = ListResponseStructure.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listResponseStructure);
    }

    public static ResponseEntity<SimpleErrorResponse> error(HttpStatus status, String message) {
        SimpleErrorResponse error = SimpleErrorResponse.builder()
                .type(status.name())
                .message(message)
                .status(status.value())
                .build();

        return ResponseEntity.status(status)
                .body(error);
    }
}
