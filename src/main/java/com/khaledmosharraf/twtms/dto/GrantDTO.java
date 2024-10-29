package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.Bank;
import com.khaledmosharraf.twtms.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantDTO {

    private Long id;
    private User user;
    private double amount;
    private String status;
    private String type;
    private String details;
    private Double currentPayscale;
    private Double requestedAmount;
    private Double totalSalaryWithdraw;
    private String requestedForName;
    private LocalDate requestedForDateOfBirth;
    private String relation;
    private String educationDetails;
    private String educationDetails2;
    private String diseaseName;
    private String diseaseDetails;
    private String surgery;
    private String application;
    private String oldScholarshipDetails;
    private String latestPaymentReceiptDetails;

    private String attachment;

    private String remarks;
    private LocalDate applicationDate;
}
