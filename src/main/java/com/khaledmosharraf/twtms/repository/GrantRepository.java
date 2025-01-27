package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.model.Grant;
import com.khaledmosharraf.twtms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface GrantRepository extends JpaRepository<Grant,Long> {
    @Query("SELECT sp FROM Grant sp JOIN sp.user u WHERE u.id = :id  ORDER BY sp.id DESC ")
    List<Grant> findByUserId(@Param("id") Long userId);

    // Find payments by username (requires a join with the User entity)
    @Query("SELECT sp FROM Grant sp JOIN sp.user u WHERE u.username = :username  ORDER BY sp.id DESC ")
    List<Grant> findByUsername(@Param("username") String username);
    @Query("SELECT sp FROM Grant sp JOIN sp.user u WHERE u.username = :username AND sp.status='Accepted' ORDER BY sp.id DESC ")
    List<Grant> findByUsernameOnlyAccepted(@Param("username") String username);

    @Query("SELECT g FROM Grant g ORDER BY g.id DESC")
    List<Grant> findAllGrant();
    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.district.id = :districtId  ORDER BY u.id DESC")
    List<Grant> findByDistrictId(@Param("districtId") Long districtId);
    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.id = :subDistrictId  ORDER BY u.id DESC")
    List<Grant> findBySubDistrictId(@Param("subDistrictId") Long subDistrictId);
    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.district.id = :districtId AND u.user.subDistrict.id = :subDistrictId  ORDER BY u.id DESC ")
    List<Grant> findByDistrictIdAndSubDistrictId(@Param("districtId") Long districtId, @Param("subDistrictId") Long subDistrictId);

    @Query("SELECT g FROM Grant g WHERE g.createdDate BETWEEN :fromDate AND :toDate ORDER BY g.id DESC")
    List<Grant> findAllGrantsByDates(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.district.id = :districtId " +
            "AND u.createdDate BETWEEN :fromDate AND :toDate ORDER BY u.id DESC")
    List<Grant> findByDistrictIdByDates(@Param("districtId") Long districtId,
                                        @Param("fromDate") LocalDateTime fromDate,
                                        @Param("toDate") LocalDateTime toDate);

    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.id = :subDistrictId " +
            "AND u.createdDate BETWEEN :fromDate AND :toDate ORDER BY u.id DESC")
    List<Grant> findBySubDistrictIdByDates(@Param("subDistrictId") Long subDistrictId,
                                           @Param("fromDate") LocalDateTime fromDate,
                                           @Param("toDate") LocalDateTime toDate);

    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.district.id = :districtId " +
            "AND u.user.subDistrict.id = :subDistrictId " +
            "AND u.createdDate BETWEEN :fromDate AND :toDate ORDER BY u.id DESC")
    List<Grant> findByDistrictIdAndSubDistrictIdByDates(@Param("districtId") Long districtId,
                                                        @Param("subDistrictId") Long subDistrictId,
                                                        @Param("fromDate") LocalDateTime fromDate,
                                                        @Param("toDate") LocalDateTime toDate);
    Long countByStatus(String status);
    Long countByStatusAndUser_SubDistrict_Id(String status, Long subDistrictId);
    Long countByUser_SubDistrict_Id(Long subDistrictId);
}
