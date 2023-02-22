package com.company.evgeniy.auto_shop.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class JwtAuthFilter implements Filter {
    String jwtSecretKey;

    public void setJwtSecretKey(String key) {
        this.jwtSecretKey = key;
    }

    public void doFilter(ServletRequest httpRequest, ServletResponse httpResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) httpRequest;
        HttpServletResponse response = (HttpServletResponse) httpResponse;
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader != null ? authorizationHeader.split(" ")[1] : null;
        if ( accessToken == null ) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
