package com.khaledmosharraf.twtms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
    private final String externalApiUrl = "https://api.ipemis.training.dpe.gov.bd/api/authenticate"; // Replace with your API URL
    AuthenticationSuccessHandler successHandler;
    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationManager(authenticationManager);
        this.successHandler = successHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // Call the external API to check the credentials
        boolean isValid = checkWithExternalApi(username, password);
        logger.debug(username);

        if (isValid) {
            AuthenticationManager authenticationManager = getAuthenticationManager();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authResult = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            return authResult;
        } else {
            throw new AuthenticationException("Invalid username or password") {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Set the SecurityContextHolder with the authentication result
        logger.debug("Successful authentication for user: {}", authResult.getName());

        setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        SecurityContext context = SecurityContextHolder.getContext();
        logger.debug("Current SecurityContext: " + context.getAuthentication());
       // successHandler.onAuthenticationSuccess(request,response,chain,authResult);
        super.successfulAuthentication(request, response, chain, authResult);
    }

    private boolean checkWithExternalApi(String username, String password) {
        // Implement actual API call here
        return true;
    }
}
