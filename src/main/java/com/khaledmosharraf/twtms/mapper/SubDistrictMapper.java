package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.model.SubDistrict;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubDistrictMapper {

    @Mapping(source = "district_id", target = "district.id")
    SubDistrict toModel(SubDistrictDTO subDistrictDTO);

    @Mapping(source = "district.id", target = "district_id")
    SubDistrictDTO toDTO(SubDistrict subDistrict);

}
