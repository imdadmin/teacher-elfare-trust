package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.model.Bank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {
    Bank toModel(BankDTO bankDTO);
    BankDTO toDTO(Bank bank);
}
