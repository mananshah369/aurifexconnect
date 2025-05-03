package com.erp.Dto.Response;

import com.erp.Enum.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
}
