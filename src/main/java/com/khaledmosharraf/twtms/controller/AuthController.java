package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.UserRepository;
import com.khaledmosharraf.twtms.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    UserRepository userRepository;

    @GetMapping("login")
    public String gotoLoginPage(Model model){
        model.addAttribute("pageTitle","Login Page");
        User user = userRepository.findByUsername("admin").orElse(new User());
    //    logger.info("User found: {}", user);

        return "auth/loginPage";
    }

}
