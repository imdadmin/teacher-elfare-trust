package com.khaledmosharraf.twtms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictRequestDTO {
    private Long id;
    @NotNull
    @Size(max = 10)
    private String e_name;

    @NotNull
    private String b_name;
}
