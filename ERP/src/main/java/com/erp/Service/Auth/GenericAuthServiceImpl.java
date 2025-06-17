package com.erp.Service.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;
import com.erp.Exception.User.UserInActiveException;
import com.erp.Model.GenericUser;
import com.erp.Security.Filter.TokenBlackListService;
import com.erp.Security.JWT.ClaimName;
import com.erp.Security.JWT.JWTService;
import com.erp.Security.util.CookieManager;
import com.erp.Security.util.UserRepositoryRegistry;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
@Slf4j
public class GenericAuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepositoryRegistry userRepositoryRegistry;
    private final TokenBlackListService tokenBlackListService;
    private final JWTService jwtService;
    private final CookieManager cookieManager;

    @Override
    public AuthRecord login(LoginRequest loginRequest) {
        log.info("Attempting login for email: {}", loginRequest.email());
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
            Authentication authentication = authenticationManager.authenticate(token);

            if (authentication.isAuthenticated()) {
                GenericUser user = userRepositoryRegistry.findUserByEmail(loginRequest.email())
                        .orElseThrow(() -> {
                            log.error("User not found after authentication: {}", loginRequest.email());
                            return new UsernameNotFoundException("User not found: " + loginRequest.email());
                        });

                if (!user.isActive()) {
                    log.warn("Login attempt for inactive user: {}", user.getEmail());
                    throw new UserInActiveException("User account is inactive. Please contact admin.");
                }


                log.info("Login successful for user: {}", user.getEmail());
                return createAuthRecordFromUser(user);
            } else {
                log.error("Authentication failed for email: {}", loginRequest.email());
                throw new UsernameNotFoundException("Failed to authenticate: " + loginRequest.email());
            }
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for email: {}", loginRequest.email(), e);
            throw new BadCredentialsException("Invalid email or password");
        } catch (AuthenticationException e) {
            log.error("Authentication error for email: {}", loginRequest.email(), e);
            throw new AuthenticationException("Authentication failed: " + e.getMessage(), e) {};
        }
    }

    @Override
    public AuthRecord refreshLogin(String refreshToken) {
        log.info("Attempting refresh login with token");
        try {
            Claims claims = jwtService.parseToken(refreshToken);
            String email = claims.get(ClaimName.USER_EMAIL, String.class);
            long refreshExpiration = claims.getExpiration().toInstant().toEpochMilli();

            GenericUser user = userRepositoryRegistry.findUserByEmail(email)
                    .orElseThrow(() -> {
                        log.error("User not found for refresh token: {}", email);
                        return new UsernameNotFoundException("User not found: " + email);
                    });

            log.info("Refresh login successful for user: {}", email);
            long accessExpiration = Instant.now().plusSeconds(3600).toEpochMilli();
            return new AuthRecord(
                    user.getId(),
                    email,
                    user.isActive(),
                    accessExpiration,
                    refreshExpiration
            );
        } catch (Exception e) {
            log.error("Refresh login failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public HttpHeaders logout(String refreshToken, String accessToken) {
        log.info("Attempting logout for tokens - access: {}, refresh: {}", accessToken, refreshToken);
        try {
            tokenBlackListService.blackListToken(refreshToken);
            tokenBlackListService.blackListToken(accessToken);

            String refreshCookie = cookieManager.generateCookie("rt", "", 0);
            String accessCookie = cookieManager.generateCookie("at", "", 0);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, refreshCookie);
            headers.add(HttpHeaders.SET_COOKIE, accessCookie);
            log.info("Logout successful, cookies cleared");
            return headers;
        } catch (Exception e) {
            log.error("Logout failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private AuthRecord createAuthRecordFromUser(GenericUser user) {
        Instant now = Instant.now();
        long accessExpiration = now.plusSeconds(3600).toEpochMilli();
        long refreshExpiration = now.plusSeconds(60 * 24 * 60 * 60L).toEpochMilli();

        return new AuthRecord(
                user.getId(),
                user.getEmail(),
                user.isActive(),
                accessExpiration,
                refreshExpiration
        );
    }
}