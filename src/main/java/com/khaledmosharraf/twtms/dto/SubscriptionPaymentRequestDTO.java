package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.User;
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
    private double amount;
    private String details;
    private LocalDate paymentDate;
}
