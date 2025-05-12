package com.erp.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponse {

    private long userId;

    private String firstName;

    private String lastName;

    private String email;

    private long phoneNo;

    private String password;

    private LocalDate createdAt;

    private LocalDate lastModifiedAt;
}
