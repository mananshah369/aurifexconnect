package com.erp.Mapper.User;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;
import com.erp.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    User mapToUserEntity(UserRequest userRequest);
    UserResponse mapToUserResponse(User user);
    List<UserResponse> mapToUserList(List<User> user);
    void mapToNewUser(UserRequest userRequest, @MappingTarget User user);
}