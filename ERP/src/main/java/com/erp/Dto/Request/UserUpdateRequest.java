package com.erp.Dto.Request;

import com.erp.Dto.Constraints.ContactNumber;
import com.erp.Dto.Constraints.Name;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @Name
    private String firstName;

    @Name
    private String lastName;

    @Email
    private String email;

    @ContactNumber
    private long phoneNo;
}
