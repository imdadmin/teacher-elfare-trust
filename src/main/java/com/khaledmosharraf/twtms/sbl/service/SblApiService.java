package com.khaledmosharraf.twtms.sbl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledmosharraf.twtms.sbl.model.CreditInformation;
import com.khaledmosharraf.twtms.sbl.model.InvoiceRequest;
import com.khaledmosharraf.twtms.sbl.model.InvoiceResponse;
import com.khaledmosharraf.twtms.sbl.model.VerifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SblApiService {
    Logger logger = LoggerFactory.getLogger(SblApiService.class);
    private final RestTemplate restTemplate;



    @Value("${sbl.api.credit.serialNo}")
    private int serialNo;

    @Value("${sbl.api.credit.crAccountOrChallanNo}")
    private String crAccountOrChallanNo;


    @Value("${sbl.api.credit.tranMode}")
    private String tranMode;

    @Value("${sbl.api.credit.onBehalf}")
    private String onBehalf;

    @Value("${sbl.api.response-url}")
    private String responseUrl; // Injecting Authorization token from properties

    @Value("${sbl.api.base-url}")
    private String baseUrl; // Injecting base URL from properties

    @Value("${sbl.api.authorization}")
    private String authorization; // Injecting Authorization token from properties

    public SblApiService(RestTemplateBuilder restTemplateBuilder) {
        // Build the RestTemplate instance
        this.restTemplate = restTemplateBuilder.build();
    }

    public InvoiceResponse createPaymentRequest(InvoiceRequest invoiceRequest) {

        CreditInformation creditInformation = new CreditInformation();
        creditInformation.setSerialNo(serialNo);
        creditInformation.setCrAccountOrChallanNo(crAccountOrChallanNo);
        creditInformation.setCrAmount(invoiceRequest.getRequestTotalAmount());
        creditInformation.setTranMode(tranMode);
        creditInformation.setOnbehalf(onBehalf);
        List<CreditInformation> creditInformations = new ArrayList<CreditInformation>();
        creditInformations.add(creditInformation);

        invoiceRequest.setCreditInformations(creditInformations);
        invoiceRequest.setResponseUrl(responseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorization);
        logger.debug("invoice: "+invoiceRequest.toString());
        logger.debug("invoice2: "+creditInformation.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            // Convert Java object to JSON string
            jsonString = objectMapper.writeValueAsString(invoiceRequest);
            System.out.println("JSON String: " + jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        logger.debug("Request Entity: {}", requestEntity);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl + "/CreatePaymentRequest",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        logger.debug("Response: "+responseEntity.getBody());

        InvoiceResponse invoiceResponse = new InvoiceResponse();
        try {
            invoiceResponse = objectMapper.readValue(responseEntity.getBody(), InvoiceResponse.class);
            logger.debug("Response: "+invoiceResponse.toString());
        }
        catch (Exception e){
            logger.debug("Response: 3 "+invoiceResponse.toString());
            return new InvoiceResponse();
        }
        logger.debug("Response: 2 "+invoiceResponse.toString());
            return invoiceResponse;
    }

    public VerifyResponse verifyTransaction(String token) {
        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON); // Set content type to JSON
        headers.set("Authorization", authorization); // Use the injected authorization token
        ObjectMapper objectMapper = new ObjectMapper();
        // Create the request body
        String requestBody = "{\"Token\": \"" + token + "\"}";

        // Create the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        logger.debug("Entry: "+requestBody);
        // Send the request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl + "/TransactionVerificationWithToken",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        logger.debug("Response: "+responseEntity.getBody());

        VerifyResponse verifyResponse = new VerifyResponse();
        try {
            verifyResponse = objectMapper.readValue(responseEntity.getBody(), VerifyResponse.class);
            logger.debug("Response: "+verifyResponse.toString());
        }
        catch (Exception e){
            logger.debug("Response: 3 "+verifyResponse.toString());
            return new VerifyResponse();
        }
        logger.debug("Response: 2 "+verifyResponse.toString());
        return verifyResponse;

    }
}
