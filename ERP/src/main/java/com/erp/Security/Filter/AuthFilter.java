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
public class AuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Validating request, finding token: {}", TokenType.ACCESS.type());

        // Extract token from cookies
        Cookie[] cookies = request.getCookies();
        String token = cookies != null ? FilterHelper.extractTokenFromCookie(cookies, TokenType.ACCESS) : null;

        if (token != null) {
            log.info("Token found with name: {}", TokenType.ACCESS.type());
            Claims claims = jwtService.parseToken(token);

            String email = claims.get(ClaimName.USER_EMAIL, String.class);
            String userType = claims.get("user_type", String.class);

            if (email != null && !email.isBlank() &&
                    userType != null && !userType.isBlank()) {

                log.info("Claims: {} & {} extracted successfully", ClaimName.USER_EMAIL, "user_type");

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Create authorities list with only userType as authority
                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority(userType.toUpperCase())
                    );

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
                log.error("Invalid claims: {} & user_type", ClaimName.USER_EMAIL);
            }
        } else {
            log.warn("Token not found with name: {}", TokenType.ACCESS.type());
        }

        filterChain.doFilter(request, response);
    }
}
