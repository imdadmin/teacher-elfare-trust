package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import com.khaledmosharraf.twtms.utils.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment,Long> {
    List<SubscriptionPayment> findByUserIdOrderByPaymentDateDesc(Long userId);
    List<SubscriptionPayment> findByUserIdOrderByIdDesc(Long userId);

    // Find payments by username (requires a join with the User entity)
    @Query("SELECT sp FROM SubscriptionPayment sp JOIN sp.user u WHERE u.username = :username order by sp.id desc ")
    List<SubscriptionPayment> findByUsername(@Param("username") String username);
    @Query("SELECT MAX(p.year) FROM SubscriptionPayment p WHERE p.user.id = :userId AND p.status = '"+ PaymentStatus.SUCCESS+"' ")
    Integer findLastPaymentYear(@Param("userId") Long userId);


    @Query("SELECT sp FROM SubscriptionPayment sp JOIN sp.user u WHERE u.username = :username order by sp.id desc ")
    List<SubscriptionPayment> findByTranId(@Param("username") String username);
    SubscriptionPayment findFirstByTranId(String tranId);
    SubscriptionPayment findFirstByToken(String token);


    @Query("SELECT g FROM SubscriptionPayment g WHERE g.createdDate BETWEEN :fromDate AND :toDate ORDER BY g.id ASC")
    List<SubscriptionPayment> findAllSubscriptionPaymentsByDates(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);


    @Query("SELECT u FROM SubscriptionPayment u WHERE u.user.subDistrict.district.id = :districtId " +
            "AND u.createdDate BETWEEN :fromDate AND :toDate ORDER BY u.id ASC")
    List<SubscriptionPayment> findByDistrictIdByDates(@Param("districtId") Long districtId,
                                        @Param("fromDate") LocalDateTime fromDate,
                                        @Param("toDate") LocalDateTime toDate);

    @Query("SELECT u FROM SubscriptionPayment u WHERE u.user.subDistrict.id = :subDistrictId " +
            "AND u.createdDate BETWEEN :fromDate AND :toDate ORDER BY u.id ASC")
    List<SubscriptionPayment> findBySubDistrictIdByDates(@Param("subDistrictId") Long subDistrictId,
                                           @Param("fromDate") LocalDateTime fromDate,
                                           @Param("toDate") LocalDateTime toDate);

    @Query("SELECT u FROM SubscriptionPayment u WHERE u.user.subDistrict.district.id = :districtId " +
            "AND u.user.subDistrict.id = :subDistrictId " +
            "AND u.createdDate BETWEEN :fromDate AND :toDate ORDER BY u.id ASC")
    List<SubscriptionPayment> findByDistrictIdAndSubDistrictIdByDates(@Param("districtId") Long districtId,
                                                        @Param("subDistrictId") Long subDistrictId,
                                                        @Param("fromDate") LocalDateTime fromDate,
                                                        @Param("toDate") LocalDateTime toDate);
}
