package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.model.District;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    District toModel(DistrictDTO districtDTO);
    default int calculateSubDistrictCount(District district) {
        if (district.getSubDistricts() != null) {
            return district.getSubDistricts().size();
        } else {
            return 0;
        }
    }

    @Mapping(target = "subDistrictCount", expression = "java(calculateSubDistrictCount(district))")
    DistrictDTO toDTO(District district);

   /* @Mapping(target = "subDistrictCount", expression = "java(district.getSubDistricts() != null ? district.getSubDistricts().size() : 0)")
    DistrictDTO toDTO(District district);*/
}
