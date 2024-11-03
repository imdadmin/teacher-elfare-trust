package com.khaledmosharraf.twtms.security.config;

import com.khaledmosharraf.twtms.security.service.CustomUserDetailsService;
import com.khaledmosharraf.twtms.security.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
        String authHeader = request.getHeader("Authorization");
        logger.debug("Jwt Filter working. "+ filterChain);

        String requestId = request.getHeader("X-Request-ID"); // Or generate one
        if (requestId == null) {
            requestId = UUID.randomUUID().toString(); // Generate if not present
        }
        logger.debug("Jwt Filter working for request ID: "+ requestId);
        String jwt = null;
        String username = null;

        // Extract JWT from cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }

        if (jwt != null) {
            try {
                username = jwtService.extractUserName(jwt);
                logger.debug("JWT found: "+username+" "+jwt);

            } catch (Exception e) {
                logger.debug("JWT Failed: "+"Token is invalid or expired : "+jwt+" "+e);
            }
        }


        if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
            logger.debug("Trying to jwt validate jwt via userDetails.");
            if (jwtService.validateToken(jwt, userDetails)) {
                logger.debug("Trying jwt validation success.");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}