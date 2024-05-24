package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.DistrictRequestDTO;
import com.khaledmosharraf.twtms.model.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictRequestMapper {

    DistrictDTO toDistrictDTO(DistrictRequestDTO districtRequestDTO);
}
