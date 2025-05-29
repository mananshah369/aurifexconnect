package com.erp.Dto.Request;

import com.erp.Dto.Constraints.ContactNumber;
import com.erp.Dto.Constraints.Email;
import com.erp.Dto.Constraints.Name;
import com.erp.Dto.Constraints.Password;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequest {

    @Name
    private String name;

    @Email
    private String email;

    @ContactNumber
    private long contactNo;

    @Password
    private String password;

}


