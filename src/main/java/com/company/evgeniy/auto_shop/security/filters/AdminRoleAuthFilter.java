package com.company.evgeniy.auto_shop.security.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class AdminRoleAuthFilter implements Filter {

    String jwtSecretKey;

    public void setJwtSecretKey(String key) {
        this.jwtSecretKey = key;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader("Authorization");
        String accessToken = authHeader != null ? authHeader.split(" ")[1] : null;
        if ( accessToken == null ) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }
        Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(accessToken).getBody();
        if ( claims.get("role").equals("admin") ) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "You're not an admin. Sorry :(");
        }
    }
}
