package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment,Long> {
    List<SubscriptionPayment> findByUserIdOrderByPaymentDateDesc(Long userId);

    // Find payments by username (requires a join with the User entity)
    @Query("SELECT sp FROM SubscriptionPayment sp JOIN sp.user u WHERE u.username = :username order by sp.paymentDate desc ")
    List<SubscriptionPayment> findByUsername(@Param("username") String username);
    @Query("SELECT MAX(p.year) FROM SubscriptionPayment p WHERE p.user.id = :userId")
    Integer findLastPaymentYear(@Param("userId") Long userId);


    @Query("SELECT sp FROM SubscriptionPayment sp JOIN sp.user u WHERE u.username = :username order by sp.paymentDate desc ")
    List<SubscriptionPayment> findByTranId(@Param("username") String username);
    SubscriptionPayment findFirstByTranId(String tranId);
}
