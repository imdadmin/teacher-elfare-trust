package com.khaledmosharraf.twtms.sbl.Controller;

import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentRequestDTO;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.sbl.model.IpnRequest;
import com.khaledmosharraf.twtms.sbl.model.IpnResponse;
import com.khaledmosharraf.twtms.sbl.model.VerifyResponse;
import com.khaledmosharraf.twtms.sbl.service.SblApiService;
import com.khaledmosharraf.twtms.service.SubscriptionPaymentService;
import com.khaledmosharraf.twtms.utils.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ipn")
public class IPNController {
    Logger logger = LoggerFactory.getLogger(IPNController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SblApiService sblApiService;
    @Autowired
    SubscriptionPaymentRequestMapper subscriptionPaymentRequestMapper;

    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;
    @PostMapping(value = "/process", consumes = "application/json", produces = "application/json")
    public ResponseEntity<IpnResponse> processPayment(@RequestBody IpnRequest ipnRequest) {
            logger.debug("IPN Request: "+ipnRequest.toString());
        try {

            // Authenticate the user with the provided username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(ipnRequest.getUsername(), ipnRequest.getPassword())
            );

            VerifyResponse verifyResponse = sblApiService.verifyTransaction(ipnRequest.getToken());
            SubscriptionPaymentDTO subscriptionPaymentDTO = subscriptionPaymentService.getByToken(verifyResponse.getToken());
            SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = subscriptionPaymentRequestMapper.toSubscriptionPaymentRequestDTO(subscriptionPaymentDTO);
            subscriptionPaymentRequestDTO.setTranDate(verifyResponse.getTransactionDate());
            IpnResponse response = new IpnResponse();
            if(verifyResponse.getStatus().equals("200")){
                response.setMessage("Success");
                response.setStatus("200");
                subscriptionPaymentRequestDTO.setStatus(PaymentStatus.SUCCESS);
            }
            else {
                response.setMessage("Unsuccessful");
                response.setStatus("403");
                subscriptionPaymentRequestDTO.setStatus(PaymentStatus.CANCELED);
            }
            subscriptionPaymentRequestDTO.setBankTranId(verifyResponse.getTransactionId());
            subscriptionPaymentRequestDTO.setTranId(verifyResponse.getTransactionId());
            subscriptionPaymentRequestDTO.setInvoiceDate(verifyResponse.getInvoiceDate());

            subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));

            // If authentication is successful, proceed with processing the request

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException e) {
            // If authentication fails, return an error response
            IpnResponse response = new IpnResponse();
            response.setMessage("Authentication Failed: " + e.getMessage());
            response.setStatus("401");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}

