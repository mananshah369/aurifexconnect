package com.erp.Service.Auth;

import com.erp.Dto.Request.AuthRecord;
import com.erp.Dto.Request.LoginRequest;
import com.erp.Model.RootUser;
import com.erp.Repository.Rootuser.RootUserRepository;
import com.erp.Security.Filter.TokenBlackListService;
import com.erp.Security.JWT.ClaimName;
import com.erp.Security.JWT.JWTService;
import com.erp.Security.util.CookieManager;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;


public class RootAuthServiceImpl  {

//    private final AuthenticationManager authenticationManager;
//    private final RootUserRepository rootUserRepository;
//    private final TokenBlackListService tokenBlackList;
//    private final JWTService jwtService;
//    private final CookieManager cookieManager;
//
//
//    @Override
//    public AuthRecord login(LoginRequest loginRequest) {
//        log.info("Attempting login for email: {}", loginRequest.email());
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
//        Authentication authentication = authenticationManager.authenticate(token);
//
//        if (authentication.isAuthenticated()) {
//            RootUser rootUser = rootUserRepository.findByEmail(loginRequest.email())
//                    .orElseThrow(() -> {
//                        log.error("ROOT_USER not found for email: {}", loginRequest.email());
//                        return new UsernameNotFoundException("ROOT_USER not found!");
//                    });
//            log.info("User found: {}", rootUser.getEmail());
//            return createAuthRecordFromRootUser(rootUser);
//        } else {
//            log.error("Authentication failed for email: {}", loginRequest.email());
//            throw new UsernameNotFoundException("Failed to authenticate!");
//        }
//    }
//
//    @Override
//    public AuthRecord refreshLogin(String refreshToken) {
//        Claims claims = jwtService.parseToken(refreshToken);
//
//        long referenceExpiration = claims.getExpiration().toInstant().toEpochMilli();
//        String email = claims.get(ClaimName.USER_EMAIL, String.class);// fixed for root user
//        boolean isActive = true;
//
//
//        RootUser rootUser = rootUserRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("ROOT_USER not found!"));
//
//        long accessExpiration = Instant.now().plusSeconds(3600).toEpochMilli();
//
//        return new AuthRecord(
//                rootUser.getId(),
//                email,
//                isActive,
//                accessExpiration,
//                referenceExpiration
//        );
//    }
//
//    @Override
//    public HttpHeaders logout(String refreshToken, String accessToken) {
//
//        tokenBlackList.blackListToken(refreshToken);
//        tokenBlackList.blackListToken(accessToken);
//
//        String refreshCookie = cookieManager.generateCookie("rt","",0);
//        String accessCookie = cookieManager.generateCookie("at","",0);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.SET_COOKIE,refreshCookie);
//        headers.add(HttpHeaders.SET_COOKIE,accessCookie);
//
//        return headers;
//    }
//
//
//    private AuthRecord createAuthRecordFromRootUser(RootUser rootUser) {
//        long userId = rootUser.getId();
//        String email = rootUser.getEmail();
//        boolean isActive = true; // or rootUser.isActive() if available
//
//        Instant now = Instant.now();
//        long accessExpiration = now.plusSeconds(60).toEpochMilli(); // 1 hour
//        long refreshExpiration = now.plusSeconds(2 * 30 * 24 * 60 * 60L).toEpochMilli(); // 60 days
//
//        return new AuthRecord(
//                userId,
//                email,
//                isActive,
//                accessExpiration,
//                refreshExpiration
//        );
//    }


}
