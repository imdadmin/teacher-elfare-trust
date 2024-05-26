package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    @Mapping(source = "subDistrictId", target = "subDistrict.id")
    UserDTO toUserDTO(UserRequestDTO userRequestDTO);
    @Mapping(source = "subDistrict.id", target = "subDistrictId")
    UserRequestDTO toUserRequestDTO(UserDTO userDTO);

}
