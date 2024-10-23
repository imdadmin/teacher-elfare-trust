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
@Table(name = "subscription_payments")
public class SubscriptionPayment extends Autditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_payment_sequence")
    @SequenceGenerator(name = "subscription_payment_sequence", sequenceName = "SUBSCRIPTION_PAYMENT_SEQUENCE", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer year;
    private double amount;
    private String details;
    private LocalDate paymentDate;

    private String invoiceNo;
    private String invoiceDate;
    private String token;
    private String tranDate;
    private String tranId;
    private String bankTranId;
    private String status;


}
