package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.UserAuthRequestDTO;
import com.khaledmosharraf.twtms.repository.UserRepository;
import com.khaledmosharraf.twtms.security.service.CustomUserDetailsService;
import com.khaledmosharraf.twtms.security.service.ExternalAuthService;
import com.khaledmosharraf.twtms.security.service.UserAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserAuthService userAuthService;

/*    @GetMapping("loginOld")
    public String gotoLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "auth/login_old";
    }*/
    @GetMapping("login")
    public String showLoginForm(Model model ,HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return onAuthenticationSuccess(request,response,authentication);
        }
        UserAuthRequestDTO userAuthRequestDTO  = new UserAuthRequestDTO();
        model.addAttribute("user",userAuthRequestDTO);
        return "auth/login";
    }

    @PostMapping("login")
    public String submitLoginForm(
        @RequestParam String username,
        @RequestParam String password ,HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Log: " + username);

        UserAuthRequestDTO userAuthRequestDTO = new UserAuthRequestDTO();
        userAuthRequestDTO.setUsername(username);
        userAuthRequestDTO.setPassword(password);
        try {
            String jwt = userAuthService.verify(userAuthRequestDTO);
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // Use Secure flag in production
            cookie.setPath("/");
            cookie.setMaxAge(2*60 * 60); // 2 hour expiry
            response.addCookie(cookie);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                return onAuthenticationSuccess(request,response,authentication);
            }
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Login failed"+ e);
            return "redirect:/login?error=true";
        }
    }
    public String onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String redirectURL = request.getContextPath();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            redirectURL = "/admin/dashboard";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            redirectURL = "/user/dashboard";
        }
        logger.debug("redirect url: "+redirectURL);
        return "redirect:"+redirectURL;
    }
}
