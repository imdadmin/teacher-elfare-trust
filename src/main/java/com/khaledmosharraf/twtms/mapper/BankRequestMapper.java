package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.dto.BankRequestDTO;
import com.khaledmosharraf.twtms.model.Bank;
import com.khaledmosharraf.twtms.model.Bank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankRequestMapper {
    BankDTO toBankDTO(BankRequestDTO bankRequestDTO);
}
