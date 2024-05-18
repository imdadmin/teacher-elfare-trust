package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.District;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
