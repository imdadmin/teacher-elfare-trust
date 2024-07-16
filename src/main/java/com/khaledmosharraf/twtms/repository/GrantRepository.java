package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Grant;
import com.khaledmosharraf.twtms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrantRepository extends JpaRepository<Grant,Long> {
    List<Grant> findByUserId(Long userId);

    // Find payments by username (requires a join with the User entity)
    @Query("SELECT sp FROM Grant sp JOIN sp.user u WHERE u.username = :username")
    List<Grant> findByUsername(@Param("username") String username);

    @Query("SELECT g FROM Grant g ORDER BY g.createdDate ASC")
    List<Grant> findAllGrant();
    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.district.id = :districtId   ORDER BY u.createdDate ASC")
    List<Grant> findByDistrictId(@Param("districtId") Long districtId);
    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.id = :subDistrictId  ORDER BY u.createdDate ASC")
    List<Grant> findBySubDistrictId(@Param("subDistrictId") Long subDistrictId);
    @Query("SELECT u FROM Grant u WHERE u.user.subDistrict.district.id = :districtId AND u.user.subDistrict.id = :subDistrictId  ORDER BY u.createdDate ASC ")
    List<Grant> findByDistrictIdAndSubDistrictId(@Param("districtId") Long districtId, @Param("subDistrictId") Long subDistrictId);
    Long countByStatus(String status);
}
