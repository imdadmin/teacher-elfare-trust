package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.Bank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequestDTO {
    private Long id;

    @NotNull
    private Long bankId;
    @NotNull
    @DecimalMin(value = "0.0", message = "{validation.amount}")
    private Double amount;

    @NotBlank
    private String transactionType;
    private String chequeNo;
    private String chequeBank;
    private String chequeDate;
    @NotNull
    private LocalDate transactionDate;
    private String heading;
    private String comments;

}
