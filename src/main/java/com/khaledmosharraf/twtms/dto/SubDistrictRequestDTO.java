package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.District;
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
public class SubDistrictRequestDTO {
    private Long id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s][\\sa-zA-Z]*$", message = "{pattern.alphaspace}")
    @Size(max = 100)
    private String e_name;

    @NotNull
    @Pattern(regexp = "^[\\u0980-\\u09FF\\s]+$", message = "{pattern.bengali}")
    @Size(max = 300)
    private String b_name;
    @NotNull(message = "District ID is mandatory")
    private Long district_id;
}
