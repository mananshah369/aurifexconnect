package com.erp.Exception.ServiceType.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceTypeNotFoundByIdException extends RuntimeException {
    private final String message ;
}
