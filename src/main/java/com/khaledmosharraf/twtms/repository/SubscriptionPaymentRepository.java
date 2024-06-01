package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment,Long> {
    List<SubscriptionPayment> findByUserId(Long userId);

    // Find payments by username (requires a join with the User entity)
    @Query("SELECT sp FROM SubscriptionPayment sp JOIN sp.user u WHERE u.username = :username")
    List<SubscriptionPayment> findByUsername(@Param("username") String username);
}
