package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bank extends Autditable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_sequence")
    @SequenceGenerator(name = "bank_sequence", sequenceName = "BANK_SEQUENCE", allocationSize = 1)

    private Long id;
    private String name;
    private String account_no;
}
