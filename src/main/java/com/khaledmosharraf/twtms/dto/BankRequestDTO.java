package com.khaledmosharraf.twtms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankRequestDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 80, message = "{validation.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{pattern.nametype}")
    private String name;

    @NotBlank
    @Size(min = 2, max = 40, message = "{validation.size}")
    @Pattern(regexp = "^[0-9\\s]+$", message = "{pattern.accounttype}")
    private String accountNo;

}
