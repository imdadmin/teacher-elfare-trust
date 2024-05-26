package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictwithSubDistrictListDTO {
    private Long id;
    private String eName;
    private String bName;
    private List<SubDistrictDTO> subDistricts;
}
