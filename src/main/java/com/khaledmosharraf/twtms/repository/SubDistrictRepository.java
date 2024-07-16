package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.model.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubDistrictRepository extends JpaRepository<SubDistrict,Long> {
    Optional<SubDistrict> findTopByOrderByIdAsc();
    @Query("SELECT s FROM SubDistrict s ORDER BY s.eName ASC")
    List<SubDistrict> findAllOrderedByEName();
}
