package com.khaledmosharraf.twtms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyfeeRequestDTO {
    private Long id;
    private Integer year;
    private Double amount;
    private String details;
}
