package com.khaledmosharraf.twtms.sbl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IpnRequest {

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("InvoiceNo")
    private String invoiceNo;

    @JsonProperty("InvoiceDate")
    private String invoiceDate;

    @JsonProperty("TransactionId")
    private String transactionId;
}
