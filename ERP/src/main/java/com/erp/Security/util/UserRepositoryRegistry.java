package com.erp.Security.util;

import com.erp.Model.Admin;
import com.erp.Model.GenericUser;
import com.erp.Model.RootUser;

import com.erp.Model.User;
import com.erp.Repository.Admin.AdminUserRepository;
import com.erp.Repository.GenericUserRepository;
import com.erp.Repository.Rootuser.RootUserRepository;
import com.erp.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class UserRepositoryRegistry {
    private final RootUserRepository rootUserRepository;
    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;

    public Optional<GenericUser> findUserByEmail(String email) {
        log.debug("Searching for user with email: {}", email);

        // Check RootUser
        Optional<RootUser> rootUser = rootUserRepository.findByEmail(email);
        if (rootUser.isPresent()) {
            log.debug("Found RootUser: {}", email);
            return Optional.of(rootUser.get());
        }

        // Check Admin
        Optional<Admin> admin = adminUserRepository.findByEmail(email);
        if (admin.isPresent()) {
            log.debug("Found Admin: {}", email);
            return Optional.of(admin.get());
        }

        // Check User
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            log.debug("Found User: {}", email);
            return Optional.of(user.get());
        }

        log.debug("No user found for email: {}", email);
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public <T extends GenericUser> GenericUserRepository<T> getRepositoryForUser(GenericUser user) {
        if (user instanceof RootUser) {
            log.debug("Returning RootUserRepository for user: {}", user.getEmail());
            return (GenericUserRepository<T>) rootUserRepository;
        } else if (user instanceof Admin) {
            log.debug("Returning AdminUserRepository for user: {}", user.getEmail());
            return (GenericUserRepository<T>) adminUserRepository;
        } else if (user instanceof User) {
            log.debug("Returning UserRepository for user: {}", user.getEmail());
            return (GenericUserRepository<T>) userRepository;
        }
        log.error("Unknown user type: {}", user.getClass().getName());
        throw new IllegalArgumentException("Unknown user type: " + user.getClass().getName());
    }
}