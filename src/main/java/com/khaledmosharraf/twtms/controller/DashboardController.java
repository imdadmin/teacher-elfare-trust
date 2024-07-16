package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.repository.*;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("username")
public class DashboardController {

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

    @GetMapping(UrlConstants.Common.ADMIN_DASHBOARD)
    public String showGrantList(Model model){

        Long totalDistrict = districtRepository.count();
        Long totalSubDistrict = subDistrictRepository.count();
        Double totalExpense = expenseRepository.getTotalExpenseAmount();
        Long totalGrants = grantRepository.count();
        Long totaluser = userRepository.count();
        Long totalsub = subscriptionPaymentRepository.count();
        Long acceptedGrants = grantRepository.countByStatus("Accepted");
        Long rejectedGrants = grantRepository.countByStatus("Rejected");
        model.addAttribute("acceptedGrants", acceptedGrants);
        model.addAttribute("totalDistrict", totalDistrict);
        model.addAttribute("totalSubDistrict", totalSubDistrict);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("totalGrants", totalGrants);
        model.addAttribute("rejectedGrants", rejectedGrants);
        model.addAttribute("totaluser", totaluser);
        model.addAttribute("totalsub", totalsub);
        model.addAttribute("pageTitle", "Dashboard Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        return "adminPanel/index";
    }

    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
