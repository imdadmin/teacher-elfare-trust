package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SSLCOMMERZPaymentRequest {
    private String store_id;
    private String store_passwd;
    private double total_amount;
    private String currency;
    private String tran_id;
    private String success_url;
    private String fail_url;
    private String cancel_url;
    private String cus_name;
    private String cus_email;
    private String cus_phone;

}
