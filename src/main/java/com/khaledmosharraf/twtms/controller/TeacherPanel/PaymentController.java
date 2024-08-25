package com.khaledmosharraf.twtms.controller.TeacherPanel;

import com.khaledmosharraf.twtms.commerz.SSLCommerz;
import com.khaledmosharraf.twtms.commerz.TransactionInitiator;
import com.khaledmosharraf.twtms.commerz.Utility.ParameterBuilder;
import com.khaledmosharraf.twtms.dto.SSLCOMMERZPaymentRequest;
import com.khaledmosharraf.twtms.service.SSLCOMMERZService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    private SSLCOMMERZService sslcommerzService;

    @GetMapping("/user/stop")
    public RedirectView initiatePayment2(@RequestParam double amount) throws Exception {
        SSLCOMMERZPaymentRequest paymentRequest = new SSLCOMMERZPaymentRequest();
        paymentRequest.setStore_id("autom66bab9691a49f");
        paymentRequest.setStore_passwd("autom66bab9691a49f@ssl");
        paymentRequest.setTotal_amount(amount);
        paymentRequest.setCurrency("BDT");
        paymentRequest.setTran_id("TEST_TRX_" + System.currentTimeMillis());
        paymentRequest.setSuccess_url("http://www.testdomain.com:8080/user/success");
        paymentRequest.setFail_url("http://www.testdomain.com:8080/user/fail");
        paymentRequest.setCancel_url("http://www.testdomain.com:8080/user/cancel");
        paymentRequest.setCus_name("khaledmosharraf");
        paymentRequest.setCus_email("khaledmosharraf21@gmail.com");
        paymentRequest.setCus_phone("01843298326");

        String paymentUrl = sslcommerzService.initiatePayment(paymentRequest);

        // Redirect user to the SSLCOMMERZ payment page
        return new RedirectView(paymentUrl);
    }

}

