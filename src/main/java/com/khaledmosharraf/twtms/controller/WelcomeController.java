package com.khaledmosharraf.twtms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("username")
public class WelcomeController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping({"/","/home"})
    public String ShowLangdingPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication.isAuthenticated();
        logger.debug("isAuthenticated: "+isAuthenticated);
        logger.debug("username: "+getLoggedUsername());
        logger.debug("user: "+authentication);
        if(authentication.getPrincipal().equals("anonymousUser")){
            isAuthenticated=false;
        }

        model.addAttribute("pageTitle","Welcome Page");
        model.addAttribute("username", getLoggedUsername());
        model.addAttribute("isAuthenticated", isAuthenticated);


        return "teacherPanel/home";
    }
    @GetMapping("/test")
    public String test(Model model){
        return "adminPanel/report/template";
    }

    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
