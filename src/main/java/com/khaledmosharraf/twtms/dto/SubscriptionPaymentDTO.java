package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPaymentDTO {
    private Long id;
    private User user;
    private Integer year;
    private Double amount;
    private String details;
    private LocalDate paymentDate;


    private String invoiceNo;
    private String invoiceDate;
    private String token;
    private String tranDate;
    private String tranId;
    private String bankTranId;
    private String status;
}
