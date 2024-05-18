package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.model.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    District toModel(DistrictDTO districtDTO);
    DistrictDTO toDTO(District district);
}
