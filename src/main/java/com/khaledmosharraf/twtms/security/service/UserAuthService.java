package com.khaledmosharraf.twtms.security.service;

import com.khaledmosharraf.twtms.dto.UserAuthRequestDTO;
import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.service.UserService;
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
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    public String verify(UserAuthRequestDTO user) {
        String token = null;
        try {
            token = externalAuthService.authenticate(user.getUsername(),user.getPassword(),false);
            logger.debug("token: "+token);
            Map<String, Object> userDetails = externalAuthService.getUserDetails(token);
            logger.debug("userDetails: "+userDetails.toString());
            UserRequestDTO userRequestDTO = externalAuthService.mapJsonToUserDTO(userDetails);
            UserDTO userDTO = userRequestMapper.toUserDTO(userRequestDTO);
            userService.add(userDTO);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }


        if(token!=null && token.length()>5){
            logger.debug("generation of token :");
            return jwtService.generateToken(user.getUsername());
        }
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        logger.debug("auth :"+authentication.toString());
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "failed";
        }
    }

}
