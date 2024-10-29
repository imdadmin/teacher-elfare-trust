package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
public class Expense extends Autditable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expanses_sequence")
    @SequenceGenerator(name = "expanses_sequence", sequenceName = "EXPANSES_SEQUENCE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "cheque_no", nullable = true)
    private String chequeNo;
    @Column(name = "cheque_bank", nullable = true)
    private String chequeBank;
    @Column(name = "cheque_date", nullable = true)
    private String chequeDate;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "heading", nullable = false)
    private String heading;
    @Column(name = "comments", nullable = true)
    private String comments;


}
