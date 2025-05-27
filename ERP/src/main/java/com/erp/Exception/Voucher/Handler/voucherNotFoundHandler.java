package com.erp.Exception.Voucher.Handler;

import com.erp.Exception.Voucher.VoucherNotFound;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class voucherNotFoundHandler {

    @ExceptionHandler
    ResponseEntity<SimpleErrorResponse> VoucherNotFound(VoucherNotFound e){
        return ResponseBuilder.error(HttpStatus.NOT_FOUND,e.getMessage());
    }
}
