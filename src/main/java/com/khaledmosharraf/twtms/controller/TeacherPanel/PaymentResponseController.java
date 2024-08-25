package com.khaledmosharraf.twtms.controller.TeacherPanel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentResponseController {

    @GetMapping("/user/pay/success")
    public String paymentSuccess(@RequestParam Map<String, String> params) {
        // Validate and handle the successful payment response here
        return "Payment Successful: " + params.toString();
    }

    @GetMapping("/user/pay//fail")
    public String paymentFail(@RequestParam Map<String, String> params) {
        // Handle the failed payment response here
        return "Payment Failed: " + params.toString();
    }

    @GetMapping("/user/pay//cancel")
    public String paymentCancel(@RequestParam Map<String, String> params) {
        // Handle the canceled payment response here
        return "Payment Canceled: " + params.toString();
    }
}

