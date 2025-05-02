package com.erp.Service.UserService;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest) ;
    UserResponse updateUserById(Long id, UserRequest userRequest);
    UserResponse deleteUserById(Long id);
    UserResponse findUserById(Long userId);
    List<UserResponse> getAllUsers();
    UserResponse login(String username, String password);
}
