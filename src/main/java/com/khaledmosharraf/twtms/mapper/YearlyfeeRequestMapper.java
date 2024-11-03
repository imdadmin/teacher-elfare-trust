package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.YearlyfeeDTO;
import com.khaledmosharraf.twtms.dto.YearlyfeeRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface YearlyfeeRequestMapper {
    YearlyfeeDTO toYearlyfeeDTO(YearlyfeeRequestDTO yearlyfeeRequestDTO);
    YearlyfeeRequestDTO toYearlyfeeRequestDTO(YearlyfeeDTO yearlyfeeDTO);

}
