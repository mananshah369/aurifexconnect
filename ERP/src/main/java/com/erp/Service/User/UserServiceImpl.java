package com.erp.Service.User;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;
import com.erp.Mapper.User.UserMapper;
import com.erp.Model.User;
import com.erp.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements  UserServices{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.mapToUser(userRequest);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }
}
