package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Bank extends Autditable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_sequence")
    @SequenceGenerator(name = "bank_sequence", sequenceName = "BANK_SEQUENCE", allocationSize = 1)

    private Long id;
    private String name;
    private String accountNo;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Deposit> deposits;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Expense> expenses;
}
