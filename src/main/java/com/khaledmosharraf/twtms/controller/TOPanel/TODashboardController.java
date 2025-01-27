package com.khaledmosharraf.twtms.controller.TOPanel;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.repository.*;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.service.GrantService;
import com.khaledmosharraf.twtms.service.SubDistrictService;
import com.khaledmosharraf.twtms.service.UserService;
import com.khaledmosharraf.twtms.utils.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.UUID;

@Controller
@SessionAttributes("username")
public class TODashboardController {

    @Autowired
    GrantRequestMapper grantRequestMapper;
    @Autowired
    GrantService grantService;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    UserService userService;
    @Autowired
    DistrictService districtService;
    @Autowired
    GrantRepository grantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    SubscriptionPaymentRepository subscriptionPaymentRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    SubDistrictRepository subDistrictRepository;
    Logger logger = LoggerFactory.getLogger(TODashboardController.class);
    @GetMapping(UrlConstants.Common.TO_DASHBOARD)
    public String showGrantList(Model model){

        String requestId = UUID.randomUUID().toString(); // Generate if not present
        logger.debug("Controller request ID: "+ requestId);
        logger.debug("request count: ");
        String username = getLoggedUsername();
        UserDTO userDTO = userService.getByUsername(username);
        Long subDistrictId = userDTO.getSubDistrict().getId();
        Long totaluser = userRepository.countBySubDistrict_Id(subDistrictId);
        Long totalsub = subscriptionPaymentRepository.countByStatusAndUser_SubDistrict_Id("SUCCESS",subDistrictId);
        Long totalGrants = grantRepository.countByUser_SubDistrict_Id(subDistrictId);
        Long acceptedGrants = grantRepository.countByStatusAndUser_SubDistrict_Id("Accepted",subDistrictId);
        Long rejectedGrants = grantRepository.countByStatusAndUser_SubDistrict_Id("Rejected",subDistrictId);
        model.addAttribute("acceptedGrants", acceptedGrants);
        model.addAttribute("totalGrants", totalGrants);
        model.addAttribute("rejectedGrants", rejectedGrants);
        model.addAttribute("totaluser", totaluser);
        model.addAttribute("totalsub", totalsub);
        model.addAttribute("pageTitle", "Dashboard Page");
        model.addAttribute("username",getLoggedUsername());
        return "toPanel/index";
    }

    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("to dashboard login details : "+authentication);
        return authentication.getName();
    }
}
