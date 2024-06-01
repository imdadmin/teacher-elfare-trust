package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.Grant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrantRepository extends JpaRepository<Grant,Long> {
    List<Grant> findByUserId(Long userId);

    // Find payments by username (requires a join with the User entity)
    @Query("SELECT sp FROM Grant sp JOIN sp.user u WHERE u.username = :username")
    List<Grant> findByUsername(@Param("username") String username);
}
