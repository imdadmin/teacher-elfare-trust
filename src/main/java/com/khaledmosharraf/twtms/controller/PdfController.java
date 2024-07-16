package com.khaledmosharraf.twtms.controller;

import com.itextpdf.html2pdf.HtmlConverter;
import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.PageStatus;
import com.khaledmosharraf.twtms.utils.UrlConstants;
import com.khaledmosharraf.twtms.validations.UserValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PdfController {
    private final TemplateEngine templateEngine;
    @Autowired
    UserRequestMapper userRequestMapper;
    @Autowired
    UserService userService;
    @Autowired
    GrantService grantService;
    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;
    @Autowired
    UserValidator userValidator;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    DistrictService districtService;
    public PdfController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    @GetMapping(UrlConstants.Report.GRANT)
    public String grantReportForm(Model model ) {
        List<DistrictDTO> districts = districtService.getAll();
        model.addAttribute("districts",districts);

        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("pageTitle", "User Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("activePage","/users");
        return "adminPanel/report/grant";
    }
    @GetMapping(UrlConstants.Report.MEMBER)
    public String memberReportForm(Model model ) {
        List<DistrictDTO> districts = districtService.getAll();
        model.addAttribute("districts",districts);

        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("pageTitle", "User Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("activePage","/users");
        return "adminPanel/report/member";
    }
    @GetMapping(UrlConstants.Report.PAYMENT)
    public String paymentReportForm(Model model ) {
        List<DistrictDTO> districts = districtService.getAll();
        model.addAttribute("districts",districts);

        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("pageTitle", "User Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("activePage","/users");
        return "adminPanel/report/payment";
    }
    @GetMapping("/generate")
    public void generatePdf(HttpServletResponse response, Model model) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=output.pdf");
        // Prepare Thymeleaf context
        Context context = new Context();
        model.asMap().forEach(context::setVariable);
        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("adminPanel/report/template", context);

        // Convert the HTML to PDF and write to the response output stream
        HtmlConverter.convertToPdf(htmlContent, response.getOutputStream());
    }
    @GetMapping("/admin/member/pdf")
    public void generateMemberPdf(@RequestParam(required = false) Long districtId, @RequestParam(required = false) Long subDistrictId,  HttpServletResponse response, Model model) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=output.pdf");
        // Prepare Thymeleaf context
        Context context = new Context();
        List<UserDTO> userDTOS = new ArrayList<>();
        if(districtId != null && subDistrictId != null){

            userDTOS = userService.getByDistrictIdAndSubDistrictId(districtId,subDistrictId);
        }
        else if(districtId != null){
            userDTOS = userService.getByDistrictId(districtId);
        }
        else if(subDistrictId != null){
            userDTOS = userService.getBySubDistrictId(subDistrictId);
        }
        else{
            userDTOS = userService.getAllTeacher();
        }
        String districtName = getDistrictName(districtId);
        String subDistrictName = getSubDistrictName(subDistrictId);

        String reportTitle = "Members Report";
        String reportFilteredBy = "District: " + districtName + ", Upazila: " + subDistrictName;

        model.addAttribute("reportTitle",reportTitle);
        model.addAttribute("reportFilteredBy",reportFilteredBy);
        model.addAttribute("users",userDTOS);

        model.asMap().forEach(context::setVariable);
        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("adminPanel/report/memberPdf", context);

        // Convert the HTML to PDF and write to the response output stream
        HtmlConverter.convertToPdf(htmlContent, response.getOutputStream());
    }
    @GetMapping("/admin/grant/pdf")
    public void generateGrantPdf(@RequestParam(required = false) Long districtId, @RequestParam(required = false) Long subDistrictId,  HttpServletResponse response, Model model) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=output.pdf");
        // Prepare Thymeleaf context
        Context context = new Context();
        List<GrantDTO> grantDTOS = new ArrayList<>();
        if(districtId != null && subDistrictId != null){
            grantDTOS = grantService.getByDistrictIdAndSubDistrictId(districtId,subDistrictId);
        }
        else if(districtId != null){
            grantDTOS = grantService.getByDistrictId(districtId);
        }
        else if(subDistrictId != null){
            grantDTOS = grantService.getBySubDistrictId(subDistrictId);
        }
        else{
            grantDTOS = grantService.getAllGrants();
        }
        String districtName = getDistrictName(districtId);
        String subDistrictName = getSubDistrictName(subDistrictId);

        String reportTitle = "Grant Report";
        String reportFilteredBy = "District: " + districtName + ", Upazila: " + subDistrictName;

        model.addAttribute("reportTitle",reportTitle);
        model.addAttribute("reportFilteredBy",reportFilteredBy);
        model.addAttribute("grants",grantDTOS);

        model.asMap().forEach(context::setVariable);
        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("adminPanel/report/grantPdf", context);

        // Convert the HTML to PDF and write to the response output stream
        HtmlConverter.convertToPdf(htmlContent, response.getOutputStream());
    }

    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public String getDistrictName(Long districtId) {
        if (districtId == null) return "ALL";
        return districtService.get(districtId).getEName();
    }

    public String getSubDistrictName(Long subDistrictId) {
        if (subDistrictId == null) return "ALL";
        return subDistrictService.get(subDistrictId).getEName();
    }

}
