package com.khaledmosharraf.twtms.sbl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InvoiceResponse {
    @JsonProperty("Status")
    private String status;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("RedirectToGateway")
    private String redirectToGateway;

    @JsonProperty("PaidDuplicateTokenList")
    private List<String> paidDuplicateTokenList;

    @JsonProperty("Message")
    private String message;

}

