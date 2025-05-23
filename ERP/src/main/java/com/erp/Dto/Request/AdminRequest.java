package com.erp.Dto.Request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequest {

    private String name;

    private String email;

    private long contactNo;

    private String password;

}


