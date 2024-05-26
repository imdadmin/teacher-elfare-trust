package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.DistrictwithSubDistrictListDTO;

import java.util.List;

public interface DistrictService extends CrudService<DistrictDTO,Long> {
    List<DistrictwithSubDistrictListDTO> getAllwithSubDistrictList();
}
