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
    private String status;
    private String type;
    @Column(length = 500)
    private String details;
    private Double currentPayscale;
    private Double requestedAmount;
    private Double totalSalaryWithdraw;
    private String requestedForName;
    private LocalDate requestedForDateOfBirth;
    private String relation;
    @Column(length = 500)
    private String educationDetails;
    @Column(length = 500)
    private String educationDetails2;

    @Column(length = 500)
    private String diseaseDetails;
    private String surgery;
    private String application;
    @Column(length = 500)
    private String oldScholarshipDetails;
    private String latestPaymentReceiptDetails;
    private String attachment;

}
