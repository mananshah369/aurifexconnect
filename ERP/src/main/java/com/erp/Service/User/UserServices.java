package com.erp.Service.User;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;

public interface UserServices {

    UserResponse createUser(UserRequest userRequest);
}
