package com.khaledmosharraf.twtms.controller.TeacherPanel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledmosharraf.twtms.commerz.PaymentResponse;
import com.khaledmosharraf.twtms.commerz.TransactionIdGenerator;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentRequestDTO;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.PaymentStatus;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Map;

@Controller
public class PaymentResponseController {
    Logger logger = LoggerFactory.getLogger(PaymentResponseController.class);

    @Autowired
    SubscriptionPaymentRequestMapper subscriptionPaymentRequestMapper;

    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;
    @PostMapping("pay/response/success")
    public String paymentSuccess(@RequestParam Map<String, String> params, HttpSession session,RedirectAttributes redirectAttributes) {
        // Validate and handle the successful payment response here
        logger.debug(params.toString());
        try {

            SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.getByTranId(params.get("tran_id"));
            SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
            subscriptionPaymentRequestDTO.setTranDate(params.get("tran_date"));
            subscriptionPaymentRequestDTO.setStatus(PaymentStatus.SUCCESS);
            subscriptionPaymentRequestDTO.setBankTranId(params.get("bank_tran_id"));

            subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
           // model.addAttribute("successMessage", "Payment Successful. Thank You.");
            redirectAttributes.addFlashAttribute("successMessage", "Payment Successful. Thank You.");

            return "redirect:/user/dashboard";


        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("errorMessage", "We apologize! An error has occurred.");
        return "redirect:/user/dashboard";

    }

    @PostMapping("pay/response/fail")
    public String paymentFail(@RequestParam Map<String, String> params,RedirectAttributes redirectAttributes) {
        logger.debug(params.toString());
        try {

            SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.getByTranId(params.get("tran_id"));
            SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
            subscriptionPaymentRequestDTO.setStatus(PaymentStatus.FAILED);

            subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
            redirectAttributes.addFlashAttribute("errorMessage", "We apologize! The payment has failed.");

            return "redirect:/user/dashboard";


        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("errorMessage", "We apologize! The payment has failed.");

        return "redirect:/user/dashboard";


    }

    @PostMapping("pay/response/cancel")
    public String paymentCancel(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {
        logger.debug(params.toString());
        try {

            SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.getByTranId(params.get("tran_id"));
            SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
            subscriptionPaymentRequestDTO.setStatus(PaymentStatus.CANCELED);

            subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
            redirectAttributes.addFlashAttribute("errorMessage", "We apologize! The payment has been Canceled.");

            return "redirect:/user/dashboard";


        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Sorry! Payment Canceled.");

        return "redirect:/user/dashboard";

    }
}

