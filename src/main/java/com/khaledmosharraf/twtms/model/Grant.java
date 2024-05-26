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
@Table(name = "grants")
public class Grant extends Autditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grant_sequence")
    @SequenceGenerator(name = "grant_sequence", sequenceName = "GRANT_SEQUENCE", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double amount;
    private int status;
    private int type;
    private String details;
    private Double currentPayscale;
    private Double requestedAmount;
    private Double totalSalaryWithdraw;
    private String requestedForName;
    private LocalDate requestedForDateOfBirth;
    private String relation;
    private String educationDetails;
    private String educationDetails2;
    private String diseaseDetails;
    private String oldScholarshipDetails;
    private String latestPaymentReceiptDetails;

}
