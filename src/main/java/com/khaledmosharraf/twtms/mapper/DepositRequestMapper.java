package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.DepositDTO;
import com.khaledmosharraf.twtms.dto.DepositRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepositRequestMapper {
    @Mapping(source = "bankId", target = "bank.id")
    DepositDTO toDepositDTO(DepositRequestDTO depositRequestDTO);
    @Mapping(source = "bank.id", target = "bankId")
    DepositRequestDTO toDepositRequestDTO(DepositDTO depositDTO);

}
