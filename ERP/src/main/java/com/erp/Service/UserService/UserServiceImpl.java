package com.erp.Service.UserService;

import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;
import com.erp.Exception.User.UserNotFoundException;
import com.erp.Mapper.User.UserMapper;
import com.erp.Model.User;
import com.erp.Repository.UserRepository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // Check for duplicate email only
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email '" + userRequest.getEmail() + "' is already registered");
        }
        else{
            User user = userMapper.mapToUserEntity(userRequest);
            userRepository.save(user);
            return userMapper.mapToUserResponse(user);
        }
    }

    @Override
    public UserResponse updateUserById(Long id, UserRequest userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        userMapper.mapToNewUser(userRequest,existingUser);
        return userMapper.mapToUserResponse(userRepository.save(existingUser));
    }

    @Override
    public UserResponse deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        userRepository.deleteById(id);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User Id " + userId + " not found"));
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> user = userRepository.findAll();
        return userMapper.mapToUserList(user);
    }

    @Override
    public UserResponse login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        return userMapper.mapToUserResponse(user);
    }
}