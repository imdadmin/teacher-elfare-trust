package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.DepositDTO;
import com.khaledmosharraf.twtms.mapper.DepositMapper;
import com.khaledmosharraf.twtms.model.Deposit;
import com.khaledmosharraf.twtms.repository.DepositRepository;
import com.khaledmosharraf.twtms.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositServiceImpl extends IdCheckingService<Deposit,Long> implements DepositService {

    @Autowired
    DepositRepository depositRepository;
    @Autowired
    DepositMapper depositMapper;
    public DepositServiceImpl(DepositRepository depositRepository) {
        super(depositRepository);
    }

    @Override
    public List<DepositDTO> getAll() {
        List<Deposit> deposits = depositRepository.findAll();
        return deposits.stream().map(depositMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public DepositDTO get(Long id) {
        Deposit deposit =  getIfExistById(id);
        return depositMapper.toDTO(deposit);
    }

    @Override
    public DepositDTO add(DepositDTO depositDTO) {

        Deposit deposit = depositMapper.toModel(depositDTO);
        deposit = depositRepository.save(deposit);
        return depositMapper.toDTO(deposit);
    }

    @Override
    public DepositDTO update(DepositDTO depositDTO) {
        getIfExistById(depositDTO.getId());
        Deposit deposit = depositMapper.toModel(depositDTO);
        deposit = depositRepository.save(deposit);
        return depositMapper.toDTO(deposit);
    }

    @Override
    public void delete(Long id) {
        Deposit deposit = getIfExistById(id);
        depositRepository.delete(deposit);
    }
}
