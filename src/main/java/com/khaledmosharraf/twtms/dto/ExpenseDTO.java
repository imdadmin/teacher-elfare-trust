package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private Bank bank;
    private Double amount;

    private String transactionType;
    private String chequeNo;
    private String chequeBank;
    private String chequeDate;
    private LocalDate transactionDate;
    private String heading;
    private String comments;
}
