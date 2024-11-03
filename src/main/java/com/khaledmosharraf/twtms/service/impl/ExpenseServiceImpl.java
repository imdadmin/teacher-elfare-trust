package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.ExpenseDTO;
import com.khaledmosharraf.twtms.mapper.ExpenseMapper;
import com.khaledmosharraf.twtms.model.Expense;
import com.khaledmosharraf.twtms.repository.ExpenseRepository;
import com.khaledmosharraf.twtms.service.ExpenseService;
import com.khaledmosharraf.twtms.utils.DatabaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl extends IdCheckingService<Expense,Long> implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    ExpenseMapper expenseMapper;
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        super(expenseRepository);
    }

    @Override
    public List<ExpenseDTO> getAll() {
        List<Expense> expenses = expenseRepository.findAll(DatabaseConstants.sortById_DESC);
        return expenses.stream().map(expenseMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ExpenseDTO get(Long id) {
        Expense expense =  getIfExistById(id);
        return expenseMapper.toDTO(expense);
    }

    @Override
    public ExpenseDTO add(ExpenseDTO expenseDTO) {

        Expense expense = expenseMapper.toModel(expenseDTO);
        expense = expenseRepository.save(expense);
        return expenseMapper.toDTO(expense);
    }

    @Override
    public ExpenseDTO update(ExpenseDTO expenseDTO) {
        getIfExistById(expenseDTO.getId());
        Expense expense = expenseMapper.toModel(expenseDTO);
        expense = expenseRepository.save(expense);
        return expenseMapper.toDTO(expense);
    }

    @Override
    public void delete(Long id) {
        Expense expense = getIfExistById(id);
        expenseRepository.delete(expense);
    }
}
