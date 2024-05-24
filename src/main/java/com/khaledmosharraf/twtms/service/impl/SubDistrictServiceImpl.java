package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.mapper.SubDistrictMapper;
import com.khaledmosharraf.twtms.model.SubDistrict;
import com.khaledmosharraf.twtms.repository.SubDistrictRepository;
import com.khaledmosharraf.twtms.service.SubDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubDistrictServiceImpl extends IdCheckingService<SubDistrict,Long> implements SubDistrictService {

    @Autowired
    SubDistrictRepository subDistrictRepository;
    @Autowired
    SubDistrictMapper subDistrictMapper;
    public SubDistrictServiceImpl(SubDistrictRepository subDistrictRepository) {
        super(subDistrictRepository);
    }

    @Override
    public List<SubDistrictDTO> getAll() {
        List<SubDistrict> subDistricts = subDistrictRepository.findAll();
        return subDistricts.stream().map(subDistrictMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SubDistrictDTO get(Long id) {
        SubDistrict subDistrict =  getIfExistById(id);
        return subDistrictMapper.toDTO(subDistrict);
    }

    @Override
    public SubDistrictDTO add(SubDistrictDTO subDistrictDTO) {

        SubDistrict subDistrict = subDistrictMapper.toModel(subDistrictDTO);
        subDistrict = subDistrictRepository.save(subDistrict);
        return subDistrictMapper.toDTO(subDistrict);
    }

    @Override
    public SubDistrictDTO update(SubDistrictDTO subDistrictDTO) {
        getIfExistById(subDistrictDTO.getId());
        SubDistrict subDistrict = subDistrictMapper.toModel(subDistrictDTO);
        subDistrict = subDistrictRepository.save(subDistrict);
        return subDistrictMapper.toDTO(subDistrict);
    }

    @Override
    public void delete(Long id) {
        SubDistrict subDistrict = getIfExistById(id);
        subDistrictRepository.delete(subDistrict);
    }
}
