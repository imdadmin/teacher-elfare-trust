package com.khaledmosharraf.twtms.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDTO {
    private Long id;

    @NotNull
    private Long bankId;
    @NotNull
    @DecimalMin(value = "0.0", message = "{validation.amount}")
    private Double amount;
}
