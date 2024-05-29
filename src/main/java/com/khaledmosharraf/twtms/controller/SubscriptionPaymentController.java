package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.service.SubDistrictService;
import com.khaledmosharraf.twtms.service.SubscriptionPaymentService;
import com.khaledmosharraf.twtms.service.UserService;
import com.khaledmosharraf.twtms.utils.PageStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("username")
public class SubscriptionPaymentController {

    @Autowired
    SubscriptionPaymentRequestMapper subscriptionPaymentRequestMapper;
    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    UserService userService;
    @Autowired
    DistrictService districtService;

    @GetMapping("subscriptionPayments")
    public String showSubscriptionPaymentList(Model model){
        List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getAll();
        List<DistrictDTO> districts = districtService.getAll();
        model.addAttribute("districts",districts);

        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("subscriptionPayments",subscriptionPayments);
        model.addAttribute("pageTitle", "SubscriptionPayment Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "adminPanel/subscriptionPayment/list";
        //   return  "subscriptionPayment/subscriptionPayment_list";
    }


    @GetMapping("create-subscriptionPayment")
    public String showSubscriptionPaymentForm(Model model ) {
        SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO  = new SubscriptionPaymentRequestDTO();
        model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
        List<UserDTO> users= userService.getAll();
        model.addAttribute("users", users);
        List<Integer> years = getLast7Years();
        model.addAttribute("years", years);
        model.addAttribute("pageTopic","Create New SubscriptionPayment");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);

        return "adminPanel/subscriptionPayment/create";

        //  return  "subscriptionPayment/create_subscriptionPayment";
    }

    @PostMapping("create-subscriptionPayment")
    public String submitSubscriptionPaymentForm(@Valid @ModelAttribute("subscriptionPayment") SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            List<UserDTO> users= userService.getAll();
            model.addAttribute("users", users);
            List<Integer> years = getLast7Years();
            model.addAttribute("years", years);

            model.addAttribute("pageTopic","Create New SubscriptionPayment");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/subscriptionPayment/create";
            // return  "subscriptionPayment/create_subscriptionPayment";
        }
        subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/subscriptionPayments";
    }
    @GetMapping("delete-subscriptionPayment")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        subscriptionPaymentService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/subscriptionPayments";
    }
    @GetMapping("update-subscriptionPayment")
    public String showUpdateSubscriptionPaymentForm( @RequestParam Long id , Model model) {

        SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.get(id);
        SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
        model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
        List<UserDTO> users= userService.getAll();
        model.addAttribute("users", users);
        List<Integer> years = getLast7Years();
        model.addAttribute("years", years);
        model.addAttribute("pageTopic","Edit SubscriptionPayment");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/subscriptionPayment/create";
        //  return  "subscriptionPayment/create_subscriptionPayment";
    }


    @PostMapping("update-subscriptionPayment")
    public String submitUpdateSubscriptionPaymentForm(@Valid @ModelAttribute("subscriptionPayment") SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            List<UserDTO> users= userService.getAll();
            model.addAttribute("users", users);
            List<Integer> years = getLast7Years();
            model.addAttribute("years", years);

            model.addAttribute("pageTopic","Edit SubscriptionPayment");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/subscriptionPayment/create";
            //   return  "subscriptionPayment/create_subscriptionPayment";
        }
        SubscriptionPaymentDTO subscriptionPaymentDTO= subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO);
        subscriptionPaymentService.update(subscriptionPaymentDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/subscriptionPayments";
    }
    @GetMapping("view-subscriptionPayment")
    public String showViewSubscriptionPaymentForm( @RequestParam Long id , Model model) {

        SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.get(id);
        SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
        model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
        List<UserDTO> users= userService.getAll();
        model.addAttribute("users", users);
        List<Integer> years = getLast7Years();
        model.addAttribute("years", years);
        model.addAttribute("pageTopic","View SubscriptionPayment");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/subscriptionPayment/create";

    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public List<Integer> getLast7Years() {
        int currentYear = Year.now().getValue();
        return IntStream.rangeClosed(currentYear - 6, currentYear)
                .map(i -> currentYear - (i - (currentYear - 6))) // Reverse the order
                .boxed()
                .collect(Collectors.toList());
    }

}
