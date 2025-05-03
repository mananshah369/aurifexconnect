package com.erp.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private long customerId;

    private String name;
    private String email;
    private String phone;
    private String address;
}
