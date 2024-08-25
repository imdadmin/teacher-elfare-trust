package com.khaledmosharraf.twtms.service;
import com.khaledmosharraf.twtms.dto.SSLCOMMERZPaymentRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class SSLCOMMERZService {

    private static final String SANDBOX_URL = "https://sandbox.sslcommerz.com/gwprocess/v3/api.php";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SSLCOMMERZService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String initiatePayment(SSLCOMMERZPaymentRequest paymentRequest) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = objectMapper.writeValueAsString(paymentRequest);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(SANDBOX_URL, HttpMethod.POST, entity, String.class);

        // Parse and return the GatewayPageURL from the response
        Map<String, String> responseMap = objectMapper.readValue(response.getBody(), Map.class);
        return responseMap.get("GatewayPageURL");
    }

}

