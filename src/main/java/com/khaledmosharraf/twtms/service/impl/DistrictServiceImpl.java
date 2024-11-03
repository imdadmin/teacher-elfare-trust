package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.DistrictwithSubDistrictListDTO;
import com.khaledmosharraf.twtms.mapper.DistrictMapper;
import com.khaledmosharraf.twtms.mapper.DistrictwithSubDistrictListMapper;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.repository.DistrictRepository;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.utils.DatabaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl extends IdCheckingService<District,Long> implements DistrictService {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    DistrictMapper districtMapper;
    @Autowired
    DistrictwithSubDistrictListMapper districtwithSubDistrictListMapper;
    public DistrictServiceImpl(DistrictRepository districtRepository) {
        super(districtRepository);
    }

    @Override
    public List<DistrictDTO> getAll() {
        List<District> districts = districtRepository.findAllOrderedByEName();
        return districts.stream().map(districtMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public List<DistrictwithSubDistrictListDTO> getAllwithSubDistrictList() {
        List<District> districts = districtRepository.findAll(DatabaseConstants.sortById_ASC);
        return districts.stream().map(districtwithSubDistrictListMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public DistrictDTO get(Long id) {
        District district =  getIfExistById(id);
        return districtMapper.toDTO(district);
    }

    @Override
    public DistrictDTO add(DistrictDTO districtDTO) {

        District district = districtMapper.toModel(districtDTO);
        district = districtRepository.save(district);
        return districtMapper.toDTO(district);
    }

    @Override
    public DistrictDTO update(DistrictDTO districtDTO) {
        getIfExistById(districtDTO.getId());
        District district = districtMapper.toModel(districtDTO);
        district = districtRepository.save(district);
        return districtMapper.toDTO(district);
    }

    @Override
    public void delete(Long id) {
        District district = getIfExistById(id);
        districtRepository.delete(district);
    }
}
