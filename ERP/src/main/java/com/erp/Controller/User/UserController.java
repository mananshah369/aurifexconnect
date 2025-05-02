package com.erp.Controller.User;

import com.erp.Dto.Request.UserLoginRequest;
import com.erp.Dto.Request.UserRequest;
import com.erp.Dto.Response.UserResponse;
import com.erp.Service.UserService.UserService;
import com.erp.Utility.ListResponseStructure;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "UserAPI", description = "Create, Get, Update, Login, ......")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create User API
    @PostMapping
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseStructure<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseBuilder.success(HttpStatus.CREATED, "User Created Successfully", userResponse);
    }

    // Update User API
    @PutMapping("/{id}")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseStructure<UserResponse>> updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUserById(id, userRequest);
        return ResponseBuilder.success(HttpStatus.OK, "User Updated Successfully", userResponse);
    }

    // Get User by ID API
    @GetMapping("/{id}")
    @Operation(summary = "Find User")
    public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.findUserById(id);
        return ResponseBuilder.success(HttpStatus.OK, "User Found Successfully", userResponse);
    }

    // Get All Users API
    @GetMapping
    @Operation(summary = "Get all Journal entries of a User")
    public ResponseEntity<ListResponseStructure<UserResponse>> getAllUsers() {
        List<UserResponse> userResponse = userService.getAllUsers();
        return ResponseBuilder.success(HttpStatus.OK, "User Fetch successfully ", userResponse);
    }

    // Delete User API
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User")
    public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable Long id) {
        UserResponse userResponse = userService.deleteUserById(id);
        return ResponseBuilder.success(HttpStatus.OK, "User Deleted Successfully", userResponse);
    }

    // Login API
    @PostMapping("/login")
    @Operation(summary = "Login User")
    public ResponseEntity<ResponseStructure<UserResponse>> login(@RequestBody UserLoginRequest loginRequest) {
        UserResponse userResponse = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseBuilder.success(HttpStatus.OK, "Logged-In Successfully", userResponse);
    }
}