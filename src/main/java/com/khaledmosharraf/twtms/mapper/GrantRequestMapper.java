package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.GrantRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GrantRequestMapper {
    @Mapping(source = "userId", target = "user.id")
    GrantDTO toGrantDTO(GrantRequestDTO grantRequestDTO);
    @Mapping(source = "user.id", target = "userId")
    GrantRequestDTO toGrantRequestDTO(GrantDTO grantDTO);

}
