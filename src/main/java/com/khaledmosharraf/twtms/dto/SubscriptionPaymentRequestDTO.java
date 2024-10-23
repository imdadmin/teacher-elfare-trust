package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPaymentRequestDTO {
    private Long id;
    private Long userId;
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
