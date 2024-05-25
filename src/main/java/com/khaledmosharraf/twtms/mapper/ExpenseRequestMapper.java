package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.ExpenseDTO;
import com.khaledmosharraf.twtms.dto.ExpenseRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseRequestMapper {
    @Mapping(source = "bankId", target = "bank.id")
    ExpenseDTO toExpenseDTO(ExpenseRequestDTO expenseRequestDTO);
    @Mapping(source = "bank.id", target = "bankId")
    ExpenseRequestDTO toExpenseRequestDTO(ExpenseDTO expenseDTO);

}
