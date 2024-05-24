package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.model.SubDistrict;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubDistrictMapper {
    SubDistrict toModel(SubDistrictDTO subDistrictDTO);
    SubDistrictDTO toDTO(SubDistrict subDistrict);

}
