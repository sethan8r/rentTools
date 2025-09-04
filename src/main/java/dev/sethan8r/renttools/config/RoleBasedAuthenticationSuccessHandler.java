package dev.sethan8r.renttools.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERADMIN"))) {
            response.sendRedirect("/superadmin");
        } else  if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            response.sendRedirect("/admin");
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_COURIER"))){
            response.sendRedirect("/courier");
        } else {
            response.sendRedirect("/");
        }
    }
}