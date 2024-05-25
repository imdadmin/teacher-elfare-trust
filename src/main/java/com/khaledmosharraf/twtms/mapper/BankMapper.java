package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.model.Bank;
import com.khaledmosharraf.twtms.model.Deposit;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {
    Bank toModel(BankDTO bankDTO);
    @Mapping(target = "depositAmount", expression = "java(calculateDepositAmount(bank))")
    @Mapping(target = "expenseAmount", expression = "java(calculateExpenseAmount(bank))")
    BankDTO toDTO(Bank bank);

    default Double calculateDepositAmount(Bank bank) {
        if (bank == null) {
            return 0.0;
        }
        List<Deposit> deposits = bank.getDeposits(); // Assuming you have a method to retrieve deposits associated with the bank
        return deposits.stream()
                .mapToDouble(Deposit::getAmount)
                .sum();
    }

    default Double calculateExpenseAmount(Bank bank) {
        if (bank == null) {
            return 0.0;
        }
        List<Expense> expenses = bank.getExpenses(); // Assuming you have a method to retrieve expenses associated with the bank
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}
