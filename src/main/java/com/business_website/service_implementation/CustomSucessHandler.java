package com.business_website.service_implementation;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSucessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
       var authourities = authentication.getAuthorities();
       var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();

        switch (roles.orElse("")) {
            case "ADMIN" -> response.sendRedirect("/admin");
            case "USER" -> response.sendRedirect("/");
            default -> response.sendRedirect("/error");
        }
    }

}
