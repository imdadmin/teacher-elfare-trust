package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.mapper.BankMapper;
import com.khaledmosharraf.twtms.model.Bank;
import com.khaledmosharraf.twtms.repository.BankRepository;
import com.khaledmosharraf.twtms.service.BankService;
import com.khaledmosharraf.twtms.utils.DatabaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl extends IdCheckingService<Bank,Long> implements BankService {

    @Autowired
    BankRepository bankRepository;
    @Autowired
    BankMapper bankMapper;
    public BankServiceImpl(BankRepository bankRepository) {
        super(bankRepository);
    }

    @Override
    public List<BankDTO> getAll() {
        List<Bank> banks = bankRepository.findAll(DatabaseConstants.sortById_ASC);
        return banks.stream().map(bankMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public BankDTO get(Long id) {
        Bank bank =  getIfExistById(id);
        return bankMapper.toDTO(bank);
    }

    @Override
    public BankDTO add(BankDTO bankDTO) {

        Bank bank = bankMapper.toModel(bankDTO);
        bank = bankRepository.save(bank);
        return bankMapper.toDTO(bank);
    }

    @Override
    public BankDTO update(BankDTO bankDTO) {
        getIfExistById(bankDTO.getId());
        Bank bank = bankMapper.toModel(bankDTO);
        bank = bankRepository.save(bank);
        return bankMapper.toDTO(bank);
    }

    @Override
    public void delete(Long id) {
        Bank bank = getIfExistById(id);
        bankRepository.delete(bank);
    }
}
