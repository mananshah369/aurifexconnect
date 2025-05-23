package com.erp.Service.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;
import com.erp.Enum.UserType;
import com.erp.Model.RootUser;
import com.erp.Repository.Rootuser.RootUserRepository;
import com.erp.Security.JWT.ClaimName;
import com.erp.Security.JWT.JWTService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;

@Service
@AllArgsConstructor
public class RootAuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RootUserRepository rootUserRepository;
    private final JWTService jwtService;


    @Override
    public AuthRecord login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            RootUser rootUser = rootUserRepository.findByEmail(loginRequest.email())
                    .orElseThrow(() -> new UsernameNotFoundException("ROOT_USER not found!"));

            return createAuthRecordFromRootUser(rootUser);
        } else {
            throw new UsernameNotFoundException("Failed to authenticate!");
        }
    }

    @Override
    public AuthRecord refreshLogin(String refreshToken) {
        Claims claims = jwtService.parseToken(refreshToken);

        long referenceExpiration = claims.getExpiration().toInstant().toEpochMilli();
        String email = claims.get(ClaimName.USER_EMAIL, String.class);
        UserType userType = UserType.ROOT; // fixed for root user
        boolean isActive = true;


        RootUser rootUser = rootUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("ROOT_USER not found!"));

        long newAccessExpiration = Instant.now().plusSeconds(3600).toEpochMilli();

        return new AuthRecord(
                rootUser.getId(),
                email,
                userType,
                isActive,
                newAccessExpiration,
                referenceExpiration
        );
    }


    private AuthRecord createAuthRecordFromRootUser(RootUser rootUser) {
        long userId = rootUser.getId();
        String email = rootUser.getEmail();
        UserType userType = UserType.ROOT; // fixed for root user
        boolean isActive = true; // or rootUser.isActive() if available

        Instant now = Instant.now();
        long accessExpiration = now.plusSeconds(60).toEpochMilli(); // 1 hour
        long refreshExpiration = now.plusSeconds(2 * 30 * 24 * 60 * 60L).toEpochMilli(); // 60 days

        return new AuthRecord(
                userId,
                email,
                userType,
                isActive,
                accessExpiration,
                refreshExpiration
        );
    }


}
