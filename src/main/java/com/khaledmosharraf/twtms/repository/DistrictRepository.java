package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District,Long> {
    @Query("SELECT d FROM District d ORDER BY d.eName ASC")
    List<District> findAllOrderedByEName();
}
