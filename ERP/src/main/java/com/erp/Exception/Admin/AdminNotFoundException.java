package com.erp.Exception.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminNotFoundException extends RuntimeException {

    private String message;

}
