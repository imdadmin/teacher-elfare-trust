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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private double amount;
    private int status;
    private String details;
    private LocalDate paymentDate;


}
