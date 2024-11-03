package com.khaledmosharraf.twtms.security.service;

import com.khaledmosharraf.twtms.dto.UserAuthRequestDTO;
import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserAuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    ExternalAuthService externalAuthService;
    @Autowired
    UserService userService;
    @Autowired
    UserRequestMapper userRequestMapper;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    public Authentication verify(UserAuthRequestDTO user, HttpServletResponse response) {
        String token = null;
        UserDTO userDTO =new UserDTO();
        try {
            token = externalAuthService.authenticate(user.getUsername(),user.getPassword(),false);
            logger.debug("token: "+token);
            Map<String, Object> userDetails = externalAuthService.getUserDetails(token);
            logger.debug("User Details: "+userDetails.toString());
            UserRequestDTO userRequestDTO = externalAuthService.mapJsonToUserDTO(userDetails);
            userDTO = userRequestMapper.toUserDTO(userRequestDTO);
            userDTO = userService.add(userDTO);
        } catch (Exception e) {
            logger.debug("From EexternalAuthService Error. So go for  Auth.");
            //throw new RuntimeException(e);
        }
        Authentication authentication;
        if(token!=null && token.length()>5){
            logger.debug("Setting user :");
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getUsername());
         //   return jwtService.generateToken(user.getUsername());
            UserDetails userDetails  = customUserDetailsService.loadUserByUsername(user.getUsername());
            authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
            //authentication.setAuthenticated(true);
        }
        else{
            logger.debug("Local Login Authentication.");
            authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        }
        logger.debug("auth :"+authentication.toString());

        if (authentication.isAuthenticated()) {
            logger.debug("Authenticated in UserAuthService as :"+authentication.getAuthorities());
            String jwt = jwtService.generateToken(user.getUsername());
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            //TODO  uncomment this when run in production secure https request.
            //cookie.setSecure(true); // Use Secure flag in production
            cookie.setPath("/");
            cookie.setMaxAge(24*60 * 60); // 24 hour expiry
            response.addCookie(cookie);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
           // SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        return authentication;
    }

}
