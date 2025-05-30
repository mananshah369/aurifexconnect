package com.erp.Utility;

import com.erp.Dto.Request.AuthRecord;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class ResponseBuilder {
    public static <T> ResponseEntity<ResponseStructure<T>> success(HttpStatus status, String message, T data) {
        ResponseStructure<T> structure = ResponseStructure.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(status)
                .body(structure);
    }
    public static <T> ResponseEntity<ResponseStructure<T>> success(HttpStatus status, HttpHeaders headers, String message, T data) {
        ResponseStructure<T> structur = ResponseStructure.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status)
                .headers(headers)
                .body(structur);
    }

    public static <T> ResponseEntity<ListResponseStructure<T>> success(HttpStatus status, String message, List<T> data) {
        ListResponseStructure<T> listResponseStructure = ListResponseStructure.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(status)
                .body(listResponseStructure);
    }

    public static <T> ResponseEntity<SetResponseStructure<T>> success(HttpStatus status, String message, Set<T> data) {
        SetResponseStructure<T> setResponseStructure = SetResponseStructure.<T>builder()
                .status(status.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(status)
                .body(setResponseStructure);
    }

    public static ResponseEntity<SimpleResponseStructure> success(HttpStatus status,HttpHeaders headers, String message){
        SimpleResponseStructure simpleResponseStructure = SimpleResponseStructure.builder()
                .status(status.value())
                .message(message)
                .build();

        return ResponseEntity.status(status)
                .headers(headers)
                .body(simpleResponseStructure);
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
