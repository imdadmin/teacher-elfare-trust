package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.ExpenseDTO;
import com.khaledmosharraf.twtms.model.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense toModel(ExpenseDTO expenseDTO);
    ExpenseDTO toDTO(Expense expense);

}
