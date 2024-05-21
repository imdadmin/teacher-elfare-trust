package com.khaledmosharraf.twtms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubDistrictDTO {
    private Long id;
    private String e_name;
    private String b_name;
    private Long district_id;
}
