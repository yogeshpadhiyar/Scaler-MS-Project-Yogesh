package com.yogesh.scalermsprojectyogesh.user.service;

import com.yogesh.scalermsprojectyogesh.model.ErrorResponse;
import com.yogesh.scalermsprojectyogesh.utility.JWTTokenUtility;
import com.yogesh.scalermsprojectyogesh.utility.UtilityFunction;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserMasterService userMasterService;
    @Autowired
    private JWTTokenUtility jwtTokenUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try {
            if (UtilityFunction.isNullOrEmpty(authToken) && authToken.startsWith("Bearer ")) {
                token = authToken.substring(7);
                username = jwtTokenUtility.extractUsername(token);
            }
            if (UtilityFunction.isNullOrEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userMasterService.loadUserByUsername(username);
                if (jwtTokenUtility.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ErrorResponse errorResponse = ErrorResponse.builder().errorCode(HttpServletResponse.SC_UNAUTHORIZED).error(e.getMessage()).build();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(errorResponse.toString());
        }
    }
}
