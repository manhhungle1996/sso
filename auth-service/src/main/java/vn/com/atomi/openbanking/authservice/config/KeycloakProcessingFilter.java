package vn.com.atomi.openbanking.authservice.config;

import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class KeycloakProcessingFilter extends KeycloakAuthenticationProcessingFilter {
    public KeycloakProcessingFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try{
            String id = Optional.ofNullable(request.getHeader("x-fapi-interaction-id"))
                    .orElse(UUID.randomUUID().toString());
            logger.info("x-fapi-interaction-id [" + id + "]");
            if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                return SecurityContextHolder.getContext().getAuthentication();
            }
            response.setDateHeader("Date", System.currentTimeMillis());
            response.setHeader("x-fapi-interaction-id", id);
            return super.attemptAuthentication(request, response);
        } catch (Exception exception){
            logger.info(response.getHeader("WWW-authenticate"));
            response.setHeader("WWW-authenticate", "Invalid token");
            return null;
        }
    }
}