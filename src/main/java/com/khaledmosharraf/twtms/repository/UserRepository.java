package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.utils.DatabaseConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query("SELECT u FROM User u WHERE 'USER' MEMBER OF u.roles order by u.id")
    List<User> findAllTeacher();
    @Query("SELECT u FROM User u WHERE u.subDistrict.district.id = :districtId  AND 'USER' MEMBER OF u.roles  order by u.id")
    List<User> findByDistrictId(@Param("districtId") Long districtId);
    @Query("SELECT u FROM User u WHERE u.subDistrict.id = :subDistrictId   AND 'USER' MEMBER OF u.roles  order by u.id")
    List<User> findBySubDistrictId(@Param("subDistrictId") Long subDistrictId);
    @Query("SELECT u FROM User u WHERE u.subDistrict.district.id = :districtId AND u.subDistrict.id = :subDistrictId  AND 'USER' MEMBER OF u.roles order by u.id")
    List<User> findByDistrictIdAndSubDistrictId(@Param("districtId") Long districtId, @Param("subDistrictId") Long subDistrictId);

}
