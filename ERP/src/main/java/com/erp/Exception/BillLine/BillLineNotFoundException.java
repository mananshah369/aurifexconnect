package com.erp.Exception.BillLine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BillLineNotFoundException extends RuntimeException{

    private String message;

}
