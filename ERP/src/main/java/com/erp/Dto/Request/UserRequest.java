package com.erp.Dto.Request;


import com.erp.Model.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{3,50}$", message = "First name must be 3-50 alphabetic characters")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{3,50}$", message = "Last name must be 2-50 alphabetic characters")
    private String lastName;

    @NotBlank
    @Email(message = "Invalid email format")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email must be a valid format like user@example.com"
    )
    private String email;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10-digit Indian number starting with 6-9"
    )
    private long phoneNo;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must have 8+ characters, with uppercase, lowercase, number, and special character"
    )
    private String password;

    @NotEmpty(message = "Roles cannot be empty")
    @Size(min = 1, message = "User must have at least one role")
    private Set<Role> roles;
}
