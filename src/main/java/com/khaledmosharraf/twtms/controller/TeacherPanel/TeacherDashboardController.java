package com.khaledmosharraf.twtms.controller.TeacherPanel;

import com.khaledmosharraf.twtms.commerz.TransactionInitiator;
import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.PageStatus;
import com.khaledmosharraf.twtms.validations.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("username")
@RequestMapping("user/")
public class TeacherDashboardController {


    @Autowired
    GrantRequestMapper grantRequestMapper;
    @Autowired
    SubscriptionPaymentRequestMapper subscriptionPaymentRequestMapper;
    @Autowired
    GrantService grantService;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    UserService userService;
    @Autowired
    DistrictService districtService;
    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;


    @GetMapping("pay")
    public RedirectView initiatePayment(@RequestParam double amount) throws Exception {
        TransactionInitiator transactionInitiator = new TransactionInitiator();
        String str = transactionInitiator.initTrnxnRequest(amount);
        return new RedirectView(str);
    }
    @GetMapping("success")
    public String paymentSuccess(@RequestParam Map<String, String> params) {
        // Validate and handle the successful payment response here
        return "Payment Successful: ";
    }

    @GetMapping("fail")
    public String paymentFail(@RequestParam Map<String, String> params) {
        // Handle the failed payment response here
        return "teacherPanel/dashboard";
    }

    @GetMapping("cancel")
    public String paymentCancel(@RequestParam Map<String, String> params) {
        // Handle the canceled payment response here
        return "teacherPanel/dashboard";
    }
    @GetMapping("dashboard")
    public String showUserDashboard(Model model){
        String username = getLoggedUsername();
        UserDTO userDTO = userService.getByUsername(username);
        List<GrantDTO> grants = grantService.getByUserId(userDTO.getId());
        List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getByUserId(userDTO.getId());

        SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO  = new SubscriptionPaymentRequestDTO();
        GrantRequestDTO grantRequestDTO = new GrantRequestDTO();

        List<Integer> years = getLast7Years();
        Integer lastPaymentYear = subscriptionPaymentService.getLastPaymentYear(userDTO.getId());
        PaymentInfoDTO paymentInfo = subscriptionPaymentService.getPaymentInfo(lastPaymentYear,userDTO.getJoiningDate().getYear());

        model.addAttribute("errorFrom","no");
        model.addAttribute("years", years);
        model.addAttribute("paymentInfo", paymentInfo);
        model.addAttribute("user",userDTO);
        model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
        model.addAttribute("grant",grantRequestDTO);
        model.addAttribute("grants",grants);
        model.addAttribute("subscriptionPayments",subscriptionPayments);
        model.addAttribute("pageTitle", "Dashboard Page");

        model.addAttribute("username",getLoggedUsername());

        return "teacherPanel/dashboard";
    }

    @PostMapping("create-subscriptionPayment")
    public String submitSubscriptionPaymentForm(@Valid @ModelAttribute("subscriptionPayment") SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            String username = getLoggedUsername();
            UserDTO userDTO = userService.getByUsername(username);
            List<GrantDTO> grants = grantService.getByUserId(userDTO.getId());
            List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getByUserId(userDTO.getId());
            GrantRequestDTO grantRequestDTO = new GrantRequestDTO();

            List<Integer> years = getLast7Years();
            model.addAttribute("errorFrom","subscriptionPayment");
            model.addAttribute("years", years);
            model.addAttribute("user",userDTO);
            model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
            model.addAttribute("grant",grantRequestDTO);
            model.addAttribute("grants",grants);
            model.addAttribute("subscriptionPayments",subscriptionPayments);
            model.addAttribute("pageTitle", "Dashboard Page");

            model.addAttribute("username",getLoggedUsername());

            return "teacherPanel/dashboard";
        }
        TransactionInitiator transactionInitiator = new TransactionInitiator();
        String str = transactionInitiator.initTrnxnRequest(subscriptionPaymentRequestDTO.getAmount());
        subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/user/pay?amount="+subscriptionPaymentRequestDTO.getAmount();
    }


    @PostMapping("create-grant")
    public String submitGrantForm(@Valid @ModelAttribute("grant") GrantRequestDTO grantRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            String username = getLoggedUsername();
            UserDTO userDTO = userService.getByUsername(username);
            List<GrantDTO> grants = grantService.getByUserId(userDTO.getId());
            List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getByUserId(userDTO.getId());
            SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = new SubscriptionPaymentRequestDTO();

            List<Integer> years = getLast7Years();
            model.addAttribute("errorFrom","grant");
            model.addAttribute("years", years);
            model.addAttribute("user",userDTO);
            model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
            model.addAttribute("grant",grantRequestDTO);
            model.addAttribute("grants",grants);
            model.addAttribute("subscriptionPayments",subscriptionPayments);
            model.addAttribute("pageTitle", "Dashboard Page");

            model.addAttribute("username",getLoggedUsername());

            return "teacherPanel/dashboard";
        }
        grantRequestDTO.setStatus("Pending");
        grantService.add(grantRequestMapper.toGrantDTO(grantRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/user/dashboard";
    }

    @GetMapping("delete-grant")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        grantService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/grants";
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
