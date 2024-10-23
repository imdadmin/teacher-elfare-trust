package com.khaledmosharraf.twtms.sbl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InvoiceRequest {
    @JsonProperty("InvoiceNo")
    private String invoiceNo;

    @JsonProperty("InvoiceDate")
    private String invoiceDate;

    @JsonProperty("RequestTotalAmount")
    private Double requestTotalAmount;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("CustomerContactNo")
    private String customerContactNo;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("ResponseUrl")
    private String responseUrl;

    @JsonProperty("AllowDuplicateInvoiceNoDate")
    private String allowDuplicateInvoiceNoDate;

    @JsonProperty("CreditInformations")
    private List<CreditInformation> creditInformations;

}

