package com.khaledmosharraf.twtms.repository;

import com.khaledmosharraf.twtms.model.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubDistrictRepository extends JpaRepository<SubDistrict,Long> {
    Optional<SubDistrict> findTopByOrderByIdAsc();
}
