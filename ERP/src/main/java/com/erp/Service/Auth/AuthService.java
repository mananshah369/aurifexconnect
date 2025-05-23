package com.erp.Service.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;

public interface LoginService {

    AuthRecord login(LoginRequest request);
}
