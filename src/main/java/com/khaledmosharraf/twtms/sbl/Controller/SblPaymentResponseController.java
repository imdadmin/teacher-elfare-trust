package com.khaledmosharraf.twtms.sbl.Controller;

import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentRequestDTO;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.sbl.model.VerifyResponse;
import com.khaledmosharraf.twtms.sbl.service.SblApiService;
import com.khaledmosharraf.twtms.service.SubscriptionPaymentService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


@Controller
public class SblPaymentResponseController {
    Logger logger = LoggerFactory.getLogger(SblPaymentResponseController.class);

    @Autowired
    SubscriptionPaymentRequestMapper subscriptionPaymentRequestMapper;

    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;
    @Autowired
    SblApiService sblApiService;
    @GetMapping("pay/response")
    public String paymentSuccess(@RequestParam String  Token, @RequestParam String  Mode , HttpSession session, RedirectAttributes redirectAttributes) {
        // Validate and handle the successful payment response here
        if(Token.length()<=2){
            redirectAttributes.addFlashAttribute("errorMessage", "We apologize! An error has occurred.");
            return "redirect:/user/dashboard";
        }
        logger.debug(Token+" : "+Mode);
        VerifyResponse verifyResponse = sblApiService.verifyTransaction(Token);
        logger.debug(verifyResponse.toString());
        if(verifyResponse.getTransactionId().length()>2){
            try {

                SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.getByToken(verifyResponse.getToken());
                SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
                subscriptionPaymentRequestDTO.setTranDate(verifyResponse.getTransactionDate());
                if(verifyResponse.getStatus().equals("200")){
                    subscriptionPaymentRequestDTO.setStatus(PaymentStatus.SUCCESS);
                    redirectAttributes.addFlashAttribute("successMessage", "Payment Successful. Thank You.");

                }
                else {
                    subscriptionPaymentRequestDTO.setStatus(PaymentStatus.FAILED);
                    redirectAttributes.addFlashAttribute("errorMessage", "We apologize! The payment has failed.");
                }
                subscriptionPaymentRequestDTO.setBankTranId(verifyResponse.getTransactionId());
                subscriptionPaymentRequestDTO.setTranId(verifyResponse.getTransactionId());
                subscriptionPaymentRequestDTO.setInvoiceDate(verifyResponse.getInvoiceDate());

                subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));

                return "redirect:/user/dashboard";


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/user/dashboard";


    }

}

