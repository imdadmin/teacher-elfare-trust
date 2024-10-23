package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantRequestDTO {
    private Long id;

    @NotNull
    private Long userId;

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
    private String diseaseDetails;
    private String surgery;
    private MultipartFile application;
    private String oldScholarshipDetails;
    private String latestPaymentReceiptDetails;
    private MultipartFile attachment;
}
