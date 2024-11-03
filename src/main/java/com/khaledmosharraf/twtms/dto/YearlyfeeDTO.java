package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyfeeDTO {
    private Long id;
    private Integer year;
    private Double amount;
    private String details;
}
