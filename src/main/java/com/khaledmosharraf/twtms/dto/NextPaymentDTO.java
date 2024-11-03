package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NextPaymentDTO {
    private Integer nextPaymentYear;
    private Double nextPaymentAmount;

    // Constructors, getters, and setters
}

