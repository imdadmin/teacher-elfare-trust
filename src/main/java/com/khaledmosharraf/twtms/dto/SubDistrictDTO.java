package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubDistrictDTO {
    private Long id;
    private String eName;
    private String bName;
    private DistrictDTO district;
}
