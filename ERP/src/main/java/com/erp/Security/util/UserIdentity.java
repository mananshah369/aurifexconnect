package com.erp.Security.util;

import com.erp.Model.*;
import com.erp.Repository.Admin.AdminUserRepository;
import com.erp.Repository.Rootuser.RootUserRepository;
import com.erp.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class UserIdentity {

    private final RootUserRepository rootUserRepository;
    private final AdminUserRepository adminRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves the current authentication context.
     *
     * @return the Authentication object
     * @throws IllegalStateException if no authentication context is available
     */
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No authentication context available");
        }
        return authentication;
    }

    /**
     * Retrieves the email of the current authenticated user.
     *
     * @return the user's email
     * @throws IllegalStateException if no authenticated user is found
     */
    public String getCurrentUserEmail() {
        Authentication authentication = getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        String email = authentication.getName();
        if (email == null || email.isEmpty()) {
            throw new IllegalStateException("User email is null or empty");
        }
        return email;
    }

    /**
     * Retrieves the current authenticated user as a GenericUser.
     *
     * @return the GenericUser (RootUser, Admin, or User)
     * @throws UsernameNotFoundException if the user is not found
     */
    public GenericUser getCurrentUser() throws UsernameNotFoundException {
        String email = getCurrentUserEmail();
        return Stream.of(
                        rootUserRepository.findByEmail(email).map(GenericUser.class::cast),
                        adminRepository.findByEmail(email).map(GenericUser.class::cast),
                        userRepository.findByEmail(email).map(GenericUser.class::cast)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Invalid User: " + email));
    }

    public Set<Role> getCurrentUserRoles() throws UsernameNotFoundException {
        GenericUser user = getCurrentUser();
        if (user instanceof User) {
            return ((User) user).getRoles();
        }
        return Collections.emptySet(); // Admin and RootUser have no roles
    }

    /**
     * Validates that the current user owns the specified resource.
     *
     * @param ownerEmail the email of the resource owner
     * @throws IllegalAccessException if the current user is not the owner
     */
    public void validateOwnership(String ownerEmail) throws IllegalAccessException {
        String currentUserEmail = getCurrentUserEmail();
        if (!currentUserEmail.equals(ownerEmail)) {
            throw new IllegalAccessException(
                    String.format("User %s not allowed to access or modify resource owned by %s",
                            currentUserEmail, ownerEmail));
        }
    }
}