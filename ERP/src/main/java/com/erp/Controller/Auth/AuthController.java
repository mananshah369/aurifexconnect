package com.erp.Controller.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;
import com.erp.Service.Auth.AuthService;
import com.erp.Service.TokenGeneration.TokenGenerationService;
import com.erp.Utility.ResponseBuilder;
import com.erp.Utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.base-url}")
@AllArgsConstructor
public class AuthController {


    private final TokenGenerationService tokenGenerationService;
    private final AuthService authService;
//
//    @PostMapping("/login")
//    public ResponseEntity<ResponseStructure<AuthRecord>> login(@RequestBody LoginRequest request) {
//        AuthRecord authRecord;
//
//        // Dispatch login based on user type
//        switch (request.userType()) {
//            case ROOT -> authRecord = authService.login(request);
//            case ADMIN -> authRecord = authService.login(request);
//            case USER -> authRecord = authService.login(request);
//            default -> throw new IllegalArgumentException("Unsupported user type: " + request.userType());
//        }
//
//        // Generate tokens and attach to response
//        HttpHeaders headers = tokenGenerationService.grantAccessAndRefreshToken(authRecord);
//        return ResponseBuilder.success(HttpStatus.OK, headers, "Login successful!", authRecord);
//    }authRecord

}
