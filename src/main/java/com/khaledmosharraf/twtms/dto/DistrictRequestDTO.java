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
public class DistrictRequestDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 80, message = "{validation.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{pattern.nametype}")
    private String eName;

    @NotBlank
    @Size(min = 2, max = 80, message = "{validation.size}")
    @Pattern(regexp = "^[\\u0980-\\u09FF\\s.-]*$", message = "{pattern.bangla}")
    private String bName;

}
