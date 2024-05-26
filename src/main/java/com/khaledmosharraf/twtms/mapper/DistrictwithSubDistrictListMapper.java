package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DepositDTO;
import com.khaledmosharraf.twtms.dto.DistrictwithSubDistrictListDTO;
import com.khaledmosharraf.twtms.model.Deposit;
import com.khaledmosharraf.twtms.model.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictwithSubDistrictListMapper {
    Deposit toModel(DistrictwithSubDistrictListDTO districtwithSubDistrictListDTO);
    DistrictwithSubDistrictListDTO toDTO(District district);
}
