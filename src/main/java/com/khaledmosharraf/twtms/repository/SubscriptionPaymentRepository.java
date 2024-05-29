package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment,Long> {
}
