package com.khaledmosharraf.twtms.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping("/error")
    public String handleError(WebRequest request, Model model) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());

        // Add error attributes to the model
        model.addAttribute("status", errorAttributes.get("status"));
        model.addAttribute("error", errorAttributes.get("error"));
        model.addAttribute("message", errorAttributes.get("message"));

        return "error"; // Return the error.html template
    }

    // Optionally implement getErrorPath (deprecated in Spring 6)
}
