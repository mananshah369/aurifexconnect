package com.erp.Controller.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;
import com.erp.Dto.Request.RootAuthRecord;
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
public class RootAuthController {

    private final TokenGenerationService tokenGenerationService;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<AuthRecord>> rootLogin(@RequestBody LoginRequest loginRequest){

        AuthRecord authRecord = authService.login(loginRequest);
        HttpHeaders httpHeaders = tokenGenerationService.grantAccessAndRefreshToken(authRecord);
        return ResponseBuilder.success(HttpStatus.CREATED,httpHeaders,"login",authRecord);
    }

    @PostMapping("/refresh-login")
    public ResponseEntity<ResponseStructure<AuthRecord>> refreshLogin(@CookieValue("rt") String refreshToken){

        AuthRecord authRecord = authService.refreshLogin(refreshToken);
        HttpHeaders httpHeaders = tokenGenerationService.grantAccessAndRefreshToken(authRecord);
        return ResponseBuilder.success(HttpStatus.OK,httpHeaders,"New access token generated !!",authRecord);

    }

}
