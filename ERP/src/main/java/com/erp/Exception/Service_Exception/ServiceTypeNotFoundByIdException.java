package com.erp.Exception.Service_Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceTypeNotFoundByIdException extends RuntimeException {
    private final String message ;
}
