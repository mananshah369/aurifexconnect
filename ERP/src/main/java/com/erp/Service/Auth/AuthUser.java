package com.erp.Service.Auth;

import com.erp.Enum.UserType;

import java.util.Set;

public interface AuthUser {
    long getI();
    String getName();
    String getEmail();
    Set<String> getRoles();

}
