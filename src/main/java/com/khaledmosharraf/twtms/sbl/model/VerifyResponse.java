package com.khaledmosharraf.twtms.sbl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VerifyResponse {
    @JsonProperty("TransactionId")
    private String transactionId;

    @JsonProperty("TransactionDate")
    private String transactionDate;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("InvoiceNo")
    private String invoiceNo;

    @JsonProperty("InvoiceDate")
    private String invoiceDate;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Branch")
    private String branch;

    @JsonProperty("RequestTotalAmount")
    private Double requestTotalAmount;

    @JsonProperty("CustomerPaidAmount")
    private Double customerPaidAmount;

    @JsonProperty("ExtraCharges")
    private Double extraCharges;

    @JsonProperty("SettlementDone")
    private String settlementDone;

    @JsonProperty("SettlementDate")
    private String settlementDate;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("PayMode")
    private String payMode;

    @JsonProperty("ScrollNo")
    private String scrollNo;

    @JsonProperty("SonaliVoucherLink")
    private String sonaliVoucherLink;

    @JsonProperty("ChallanLink")
    private String challanLink;

    @JsonProperty("PartyName")
    private String partyName;

}
