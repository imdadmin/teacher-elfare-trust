package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private Bank bank;
    private Double amount;
}
