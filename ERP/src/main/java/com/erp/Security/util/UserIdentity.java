package com.erp.Security.util;

import com.erp.Model.RootUser;
import com.erp.Repository.Rootuser.RootUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIdentity {
    private final RootUserRepository rootUserRepository;

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUserEmail(){
        return getAuthentication().getName();
    }

    public RootUser getCurrentUser(){
        return rootUserRepository.findByEmail(this.getCurrentUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid User"));
    }

    public void validateOwnerShip(String ownerName) throws IllegalAccessException {
        if(!getCurrentUser().equals(ownerName))
            throw new IllegalAccessException("User Not Allow To Accsess or modifeyes Resource requested");
    }
}
