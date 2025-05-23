package com.erp.Service.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;

public interface AuthService {

    AuthRecord login(LoginRequest loginRequest);

    AuthRecord refreshLogin(String refreshToken);
}
