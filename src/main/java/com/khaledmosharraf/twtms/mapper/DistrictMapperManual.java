package com.khaledmosharraf.twtms.mapper;


import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.model.District;

import java.util.ArrayList;

public class DistrictMapperManual {
    public static DistrictDTO toDTO(District district) {
        if (district == null) {
            return null;
        }
        return new DistrictDTO(
                district.getId(),
                district.getEName(),
                district.getBName(),
                district.getSubDistricts().size()
        );
    }

    public static District toModel(DistrictDTO districtDTO) {
        if (districtDTO == null) {
            return null;
        }
        District district = new District();
        district.setId(districtDTO.getId());
        district.setEName(districtDTO.getEName());
        district.setBName(districtDTO.getBName());
        // Here, we don't map subDistricts, as it's not part of the DTO
        district.setSubDistricts(new ArrayList<>()); // Initialize the list to avoid NullPointerException
        return district;
    }
}
