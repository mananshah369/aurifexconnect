package com.erp.Dto.Request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private long phoneNo;

    private String password;
}
