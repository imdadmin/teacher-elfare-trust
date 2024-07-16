package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoDTO {
    private Integer lastPaymentYear;
    private Integer dueYears;

    // Constructors, getters, and setters
}

