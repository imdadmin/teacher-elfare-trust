package com.khaledmosharraf.twtms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

    @GetMapping("/home")
    public String ShowLangdingPage(){
        return "teacherPanel/home";
    }
}
