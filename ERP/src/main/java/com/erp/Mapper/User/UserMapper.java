package com.erp.Mapper;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;
import com.erp.Model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUser(UserRequest userRequest);

    UserResponse mapToUserResponse(User user);


}
