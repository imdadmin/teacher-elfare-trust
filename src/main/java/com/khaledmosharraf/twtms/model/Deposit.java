package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposits")
public class Deposit extends Autditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposits_sequence")
    @SequenceGenerator(name = "deposits_sequence", sequenceName = "DEPOSITS_SEQUENCE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @Column(name = "amount", nullable = false)
    private Double amount;
}
