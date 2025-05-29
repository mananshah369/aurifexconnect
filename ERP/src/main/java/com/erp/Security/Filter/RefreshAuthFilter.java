package com.erp.Security.Filter;

import com.erp.Security.JWT.ClaimName;
import com.erp.Security.JWT.JWTService;
import com.erp.Security.JWT.TokenType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class RefreshAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final TokenBlackListService tokenBlackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Validating request, finding token: {}", TokenType.REFRESH.type());
        log.info("RefreshAuthFilter handling request. Path: {}", request.getRequestURI());
        String path = request.getRequestURI();
        log.debug("Processing request in RefreshAuthFilter: {}", path);
        if (path.startsWith("/api/v1/login") || path.startsWith("/api/v1/auth/") || path.equals("/error") || path.equals("/api/v1/logout")) {
            log.debug("Skipping RefreshAuthFilter for path: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String token = cookies != null ? FilterHelper.extractTokenFromCookie(cookies, TokenType.REFRESH) : null;

        if (token != null && !tokenBlackListService.isBlackListed(token)) {
            log.info("Token found with name: {}", TokenType.REFRESH.type());
            Claims claims = jwtService.parseToken(token);

            String email = claims.get(ClaimName.USER_EMAIL, String.class);

            if (email != null && !email.isEmpty()) {
                log.info("Claims: {} extracted successfully", ClaimName.USER_EMAIL);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ROOT"));

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            authorities
                    );

                    authToken.setDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.info("Request Authentication Successfully!!");
                }
            } else {
                log.error("Invalid claims: {}", ClaimName.USER_EMAIL);
            }
        } else {
            log.warn("Token not found or blacklisted with name: {}", TokenType.REFRESH.type());
        }

        filterChain.doFilter(request, response);
    }
}