package com.khaledmosharraf.twtms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictDTO {
    private Long id;
    private String e_name;
    private String b_name;
}
