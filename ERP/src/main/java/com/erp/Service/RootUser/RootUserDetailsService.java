package com.erp.Service.RootUser;

import com.erp.Repository.Rootuser.RootUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RootUserDetailsService implements UserDetailsService {

    private final RootUserRepository rootUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return rootUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Root user not found with email: " + email));
    }
}
