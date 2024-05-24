package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.DistrictRequestDTO;
import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.dto.SubDistrictRequestDTO;
import com.khaledmosharraf.twtms.model.SubDistrict;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubDistrictRequestMapper {
    @Mapping(source = "districtId", target = "district.id")
    SubDistrictDTO toSubDistrictDTO(SubDistrictRequestDTO subDistrictRequestDTO);
    @Mapping(source = "district.id", target = "districtId")
    SubDistrictRequestDTO toSubDistrictRequestDTO(SubDistrictDTO subDistrictDTO);

}
