package com.erp.Service.User;

import com.erp.Dto.Request.RoleRequest;
import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Request.UserUpdateRequest;
import com.erp.Dto.Response.UserResponse;
import com.erp.Exception.Admin.AdminNotFoundException;
import com.erp.Exception.User.UserNotFoundException;
import com.erp.Mapper.User.UserMapper;
import com.erp.Model.Admin;
import com.erp.Model.Role;
import com.erp.Model.User;
import com.erp.Repository.Role.RoleRepository;
import com.erp.Repository.User.UserRepository;
import com.erp.Security.util.UserIdentity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserIdentity userIdentity;


    private final static String DEFAULT_ROLE = "EMPLOYEE";

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // Check for duplicate email
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = userMapper.mapToUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(new HashSet<>()); // Clear any transient roles

        // Save user first to generate ID
        user = userRepository.save(user);

        // Process roles
        Set<Role> attachedRoles = new HashSet<>();
        for (RoleRequest roleRequest : userRequest.getRoles()) {
            String roleNameUpper = roleRequest.getRoleName().toUpperCase(); // Convert to uppercase

            Role existingRole = roleRepository.findByRoleName(roleNameUpper)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setRoleName(roleNameUpper); // Save role in uppercase
                        return roleRepository.save(newRole);
                    });

            attachedRoles.add(existingRole);
        }

        // Add default role (e.g., "USER")
        Role defaultRole = roleRepository.findByRoleName(DEFAULT_ROLE)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName(DEFAULT_ROLE);
                    return roleRepository.save(newRole);
                });
        attachedRoles.add(defaultRole);

        user.setRoles(attachedRoles);
        user = userRepository.save(user); // Save again to update roles

        return userMapper.mapToUserResponse(user); // Use MapStruct for mapping
    }

    @Override
    @Transactional()
    public List<UserResponse> getListOfUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.mapToListOfUserResponse(users);
    }

    @Override
    public UserResponse updateUserById(UserUpdateRequest userUpdateRequest, long id) throws Exception{
        User user = (User) userIdentity.getCurrentUser();

        if(user.getId() == id){
            userMapper.mapTOUserEntity(userUpdateRequest,user);
        }else {
            throw new IllegalAccessException("With this id: "+ id + "currently not login !");
        }
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse deleteUserById(long id) {

        Admin currentUser = (Admin) userIdentity.getCurrentUser();
        if (currentUser == null) {
            throw new SecurityException("No authenticated user found");
        }

        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Admin not found with this id: "+id));

        user.setActive(false);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);

    }
}