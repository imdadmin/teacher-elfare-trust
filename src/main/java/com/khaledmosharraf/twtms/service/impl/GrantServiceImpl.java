package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.mapper.GrantMapper;
import com.khaledmosharraf.twtms.model.Grant;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.GrantRepository;
import com.khaledmosharraf.twtms.service.GrantService;
import com.khaledmosharraf.twtms.utils.DatabaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrantServiceImpl extends IdCheckingService<Grant,Long> implements GrantService {

    @Autowired
    GrantRepository grantRepository;
    @Autowired
    GrantMapper grantMapper;
    public GrantServiceImpl(GrantRepository grantRepository) {
        super(grantRepository);
    }

    @Override
    public List<GrantDTO> getAll() {
        List<Grant> grants = grantRepository.findAll(DatabaseConstants.sortById_DESC);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public GrantDTO get(Long id) {
        Grant grant =  getIfExistById(id);
        return grantMapper.toDTO(grant);
    }

    @Override
    public GrantDTO add(GrantDTO grantDTO) {

        Grant grant = grantMapper.toModel(grantDTO);
        grant = grantRepository.save(grant);
        return grantMapper.toDTO(grant);
    }

    @Override
    public GrantDTO update(GrantDTO grantDTO) {
        getIfExistById(grantDTO.getId());
        Grant grant = grantMapper.toModel(grantDTO);
        grant = grantRepository.save(grant);
        return grantMapper.toDTO(grant);
    }

    @Override
    public void delete(Long id) {
        Grant grant = getIfExistById(id);
        grantRepository.delete(grant);
    }
    @Override
    public List<GrantDTO> getByUserId(Long id) {
        List<Grant> grants = grantRepository.findByUserId(id);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GrantDTO> getByUsername(String username) {
        List<Grant> grants = grantRepository.findByUsername(username);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public List<GrantDTO> getByUsernameOnlyAccepted(String username) {

        List<Grant> grants = grantRepository.findByUsernameOnlyAccepted(username);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GrantDTO> getAllGrants() {
        List<Grant> grants = grantRepository.findAllGrant();
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GrantDTO> getByDistrictId(Long districtId) {

        List<Grant> grants = grantRepository.findByDistrictId(districtId);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GrantDTO> getBySubDistrictId(Long subDistrictId) {

        List<Grant> grants = grantRepository.findBySubDistrictId(subDistrictId);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<GrantDTO> getByDistrictIdAndSubDistrictId(Long districtId, Long subDistrictId) {

        List<Grant> grants = grantRepository.findByDistrictIdAndSubDistrictId(districtId,subDistrictId);
        return grants.stream().map(grantMapper::toDTO).collect(Collectors.toList());
    }


    public List<GrantDTO> getAllGrantsByDates(LocalDateTime fromDate, LocalDateTime toDate) {
        return grantRepository.findAllGrantsByDates(fromDate, toDate)
                .stream().map(grantMapper::toDTO).toList();
    }

    public List<GrantDTO> getByDistrictIdByDates(Long districtId, LocalDateTime fromDate, LocalDateTime toDate) {
        return grantRepository.findByDistrictIdByDates(districtId, fromDate, toDate)
                .stream().map(grantMapper::toDTO).toList();
    }

    public List<GrantDTO> getBySubDistrictIdByDates(Long subDistrictId, LocalDateTime fromDate, LocalDateTime toDate) {
        return grantRepository.findBySubDistrictIdByDates(subDistrictId, fromDate, toDate)
                .stream().map(grantMapper::toDTO).toList();
    }

    public List<GrantDTO> getByDistrictIdAndSubDistrictIdByDates(Long districtId, Long subDistrictId, LocalDateTime fromDate, LocalDateTime toDate) {
        return grantRepository.findByDistrictIdAndSubDistrictIdByDates(districtId, subDistrictId, fromDate, toDate)
                .stream().map(grantMapper::toDTO).toList();
    }


}
