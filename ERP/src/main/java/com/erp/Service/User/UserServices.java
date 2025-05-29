package com.erp.Service.User;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Request.UserUpdateRequest;
import com.erp.Dto.Response.UserResponse;

import java.util.List;

public interface UserServices {

    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getListOfUsers();

    UserResponse updateUserById(UserUpdateRequest userUpdateRequest, long id) throws Exception;

    UserResponse deleteUserById(long id);
}
