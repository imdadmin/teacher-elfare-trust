package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.mapper.GrantMapper;
import com.khaledmosharraf.twtms.model.Grant;
import com.khaledmosharraf.twtms.repository.GrantRepository;
import com.khaledmosharraf.twtms.service.GrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Grant> grants = grantRepository.findAll();
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
}
