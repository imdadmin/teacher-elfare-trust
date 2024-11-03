package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.YearlyfeeDTO;
import com.khaledmosharraf.twtms.model.Yearlyfee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface YearlyfeeMapper {
    Yearlyfee toModel(YearlyfeeDTO yearlyfeeDTO);
    YearlyfeeDTO toDTO(Yearlyfee yearlyfee);

}