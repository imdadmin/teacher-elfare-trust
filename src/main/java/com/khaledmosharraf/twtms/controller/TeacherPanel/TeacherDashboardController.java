package com.khaledmosharraf.twtms.controller.TeacherPanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledmosharraf.twtms.commerz.PaymentResponse;
import com.khaledmosharraf.twtms.commerz.TransactionIdGenerator;
import com.khaledmosharraf.twtms.commerz.TransactionInitiator;
import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.PageStatus;
import com.khaledmosharraf.twtms.utils.PaymentStatus;
import com.khaledmosharraf.twtms.validations.UserValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("username")
@RequestMapping("user/")
public class TeacherDashboardController {
    private final TransactionIdGenerator idGenerator = new TransactionIdGenerator();

    Logger logger = LoggerFactory.getLogger(TeacherDashboardController.class);
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
    public RedirectView initiatePayment(@RequestParam double amount ,@RequestParam String tran_id) throws Exception {
        String username = getLoggedUsername();
        TransactionInitiator transactionInitiator = new TransactionInitiator();
        String str = transactionInitiator.initTrnxnRequest(tran_id,amount,username);
        logger.debug("From Pay: "+str);
        return new RedirectView(str);
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
        LocalDate currentDate = LocalDate.now();
        subscriptionPaymentRequestDTO.setPaymentDate(currentDate);

        subscriptionPaymentRequestDTO.setTranDate("");
        subscriptionPaymentRequestDTO.setStatus(PaymentStatus.PENDING);
        String tranId = idGenerator.generateTransactionId();
        subscriptionPaymentRequestDTO.setTranId(tranId);
        subscriptionPaymentRequestDTO.setBankTranId("");
        subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
        return "redirect:/user/pay?amount="+subscriptionPaymentRequestDTO.getAmount()+"&tran_id="+tranId;
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
