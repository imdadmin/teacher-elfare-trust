package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.Bank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequestDTO {
    private Long id;

    @NotNull
    private long bankId;
    @NotNull
    @DecimalMin(value = "0.0", message = "{validation.amount}")
    private Double amount;
}
