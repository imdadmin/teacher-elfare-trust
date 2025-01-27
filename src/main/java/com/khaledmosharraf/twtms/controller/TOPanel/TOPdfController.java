package com.khaledmosharraf.twtms.controller.TOPanel;

import com.itextpdf.html2pdf.HtmlConverter;
import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.mapper.GrantMapper;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.UrlConstants;
import com.khaledmosharraf.twtms.validations.UserValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TOPdfController {
    Logger logger = LoggerFactory.getLogger(TOPdfController.class);
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
    @Autowired
    GrantMapper grantMapper;
    public TOPdfController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    @GetMapping(UrlConstants.TOReport.GRANT)
    public String grantReportForm(Model model ) {

        String username = getLoggedUsername();
        UserDTO userDTO = userService.getByUsername(username);
        SubDistrictDTO subDistrict = subDistrictService.get(userDTO.getSubDistrict().getId());
        model.addAttribute("subDistrict",subDistrict);

        model.addAttribute("pageTitle", "User Page");

        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("activePage","/users");
        return "toPanel/report/grant";
    }
    @GetMapping(UrlConstants.TOReport.MEMBER)
    public String memberReportForm(Model model ) {
        String username = getLoggedUsername();
        UserDTO userDTO = userService.getByUsername(username);
        SubDistrictDTO subDistrict = subDistrictService.get(userDTO.getSubDistrict().getId());
        model.addAttribute("subDistrict",subDistrict);

        model.addAttribute("pageTitle", "User Page");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("activePage","/users");
        return "toPanel/report/member";
    }
    @GetMapping(UrlConstants.TOReport.PAYMENT)
    public String paymentReportForm(Model model ) {
        String username = getLoggedUsername();
        UserDTO userDTO = userService.getByUsername(username);
        SubDistrictDTO subDistrict = subDistrictService.get(userDTO.getSubDistrict().getId());
        model.addAttribute("subDistrict",subDistrict);
        model.addAttribute("pageTitle", "User Page");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("activePage","/users");
        return "toPanel/report/payment";
    }

    @GetMapping("/to/member/pdf")
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
        String reportFilteredBy = "Upazila: " + subDistrictName;

        model.addAttribute("reportTitle",reportTitle);
        model.addAttribute("reportFilteredBy",reportFilteredBy);
        model.addAttribute("users",userDTOS);

        model.asMap().forEach(context::setVariable);
        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("toPanel/report/memberPdf", context);
        logger.debug(htmlContent);
        // Convert the HTML to PDF and write to the response output stream
        HtmlConverter.convertToPdf(htmlContent, response.getOutputStream());
    }
    @GetMapping("/to/grant/pdf")
    public void generateGrantPdf(@RequestParam(required = false) Long districtId, @RequestParam(required = false) Long subDistrictId, @RequestParam(required = true) LocalDate fromDate, @RequestParam(required = true) LocalDate toDate, HttpServletResponse response, Model model) throws IOException, ParseException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=output.pdf");
        // Prepare Thymeleaf context
        Context context = new Context();
        List<GrantDTO> grantDTOS = new ArrayList<>();
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);
        if(districtId != null && subDistrictId != null){
            grantDTOS = grantService.getByDistrictIdAndSubDistrictIdByDates(districtId,subDistrictId,fromDateTime,toDateTime);
        }
        else if(districtId != null){
            grantDTOS = grantService.getByDistrictIdByDates(districtId,fromDateTime,toDateTime);
        }
        else if(subDistrictId != null){
            grantDTOS = grantService.getBySubDistrictIdByDates(subDistrictId,fromDateTime,toDateTime);
        }
        else{
            grantDTOS = grantService.getAllGrantsByDates(fromDateTime,toDateTime);
        }
        String districtName = getDistrictName(districtId);
        String subDistrictName = getSubDistrictName(subDistrictId);

        String reportTitle = "Grant Report";
        String reportFilteredBy = "Upazila: " + subDistrictName+ ", From Date: " + fromDate + ", To Date: " + toDate;

        model.addAttribute("reportTitle",reportTitle);
        model.addAttribute("reportFilteredBy",reportFilteredBy);
        model.addAttribute("grants",grantDTOS);

        model.asMap().forEach(context::setVariable);
        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("toPanel/report/grantPdf", context);

        // Convert the HTML to PDF and write to the response output stream
        HtmlConverter.convertToPdf(htmlContent, response.getOutputStream());
    }
    @GetMapping("/to/payment/pdf")
    public void generatePaymentPdf(@RequestParam(required = false) Long districtId, @RequestParam(required = false) Long subDistrictId, @RequestParam(required = true) LocalDate fromDate, @RequestParam(required = true) LocalDate toDate,  HttpServletResponse response, Model model) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=output.pdf");
        // Prepare Thymeleaf context
        Context context = new Context();
        List<SubscriptionPaymentDTO> subscriptionPaymentDTOS = new ArrayList<>();
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);
        if(districtId != null && subDistrictId != null){
            subscriptionPaymentDTOS = subscriptionPaymentService.getByDistrictIdAndSubDistrictIdByDates(districtId,subDistrictId,fromDateTime,toDateTime);
        }
        else if(districtId != null){
            subscriptionPaymentDTOS = subscriptionPaymentService.getByDistrictIdByDates(districtId,fromDateTime,toDateTime);
        }
        else if(subDistrictId != null){
            subscriptionPaymentDTOS = subscriptionPaymentService.getBySubDistrictIdByDates(subDistrictId,fromDateTime,toDateTime);
        }
        else{
            subscriptionPaymentDTOS = subscriptionPaymentService.getAllSubscriptionPaymentsByDates(fromDateTime,toDateTime);
        }
        String districtName = getDistrictName(districtId);
        String subDistrictName = getSubDistrictName(subDistrictId);

        String reportTitle = "Subscription Payment Report";
        String reportFilteredBy = "Upazila: " + subDistrictName+ ", From Date: " + fromDate + ", To Date: " + toDate;

        model.addAttribute("reportTitle",reportTitle);
        model.addAttribute("reportFilteredBy",reportFilteredBy);
        model.addAttribute("subscriptionPayments",subscriptionPaymentDTOS);

        model.asMap().forEach(context::setVariable);
        // Render Thymeleaf template to HTML
        String htmlContent = templateEngine.process("toPanel/report/paymentPdf", context);

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
