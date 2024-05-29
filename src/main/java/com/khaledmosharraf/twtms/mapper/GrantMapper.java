package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.model.Grant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrantMapper {
    Grant toModel(GrantDTO grantDTO);
    GrantDTO toDTO(Grant grant);

}
