package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DepositDTO;
import com.khaledmosharraf.twtms.model.Deposit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositMapper {
    Deposit toModel(DepositDTO depositDTO);
    DepositDTO toDTO(Deposit deposit);

}
