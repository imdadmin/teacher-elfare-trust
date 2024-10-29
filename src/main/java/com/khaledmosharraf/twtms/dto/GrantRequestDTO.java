package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.User;
import jakarta.validation.constraints.*;
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
    @NotNull
    @DecimalMin(value = "0.0", message = "{validation.amount}")
    private double amount;
    private String status;
    @NotBlank
    @Size(min = 1, max = 80, message = "{validation.size}" )
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{pattern.nametype}")
    private String type;
    private String details;
    private Double currentPayscale;
    @NotNull
    @DecimalMin(value = "0.0", message = "{validation.amount}")
    private Double requestedAmount;
    private Double totalSalaryWithdraw;
    @NotBlank
    @Size(min = 1, max = 80, message = "{validation.size}" )
    private String requestedForName;
    private LocalDate requestedForDateOfBirth;
    @NotBlank
    @Size(min = 1, max = 80, message = "{validation.size}" )
    private String relation;
    private String educationDetails;
    private String educationDetails2;
    private String diseaseName;
    private String diseaseDetails;
    private String surgery;
    private MultipartFile application;
    private String oldScholarshipDetails;
    private String latestPaymentReceiptDetails;
    private MultipartFile attachment;
    private String remarks;
    private LocalDate applicationDate;
}
