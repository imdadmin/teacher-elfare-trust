package com.khaledmosharraf.twtms.sbl.Controller;

import com.khaledmosharraf.twtms.sbl.model.InvoiceRequest;
import com.khaledmosharraf.twtms.sbl.model.InvoiceResponse;
import com.khaledmosharraf.twtms.sbl.model.VerifyResponse;
import com.khaledmosharraf.twtms.sbl.service.SblApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final SblApiService sblApiService;

    @Autowired
    public InvoiceController(SblApiService sblApiService) {
        this.sblApiService = sblApiService;
    }

    @PostMapping("/create")
    public InvoiceResponse createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            InvoiceResponse response = sblApiService.createPaymentRequest(invoiceRequest);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/verify-transaction")
    public VerifyResponse verifyTransaction(@RequestBody String token) {
        try {
            // Call the service to verify the transaction
             VerifyResponse response =  sblApiService.verifyTransaction(token); // Return the response from the API
            return  response;
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return null;
        }
    }
}
