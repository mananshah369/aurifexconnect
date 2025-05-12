package com.erp.Mapper;

import com.erp.Dto.Request.UserRequest;
import com.erp.Model.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUser(UserRequest userRequest);

}
