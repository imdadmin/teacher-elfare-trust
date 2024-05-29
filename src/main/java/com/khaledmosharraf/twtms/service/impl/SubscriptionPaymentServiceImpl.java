package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentMapper;
import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import com.khaledmosharraf.twtms.repository.SubscriptionPaymentRepository;
import com.khaledmosharraf.twtms.service.SubscriptionPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionPaymentServiceImpl extends IdCheckingService<SubscriptionPayment,Long> implements SubscriptionPaymentService {

    @Autowired
    SubscriptionPaymentRepository subscriptionPaymentRepository;
    @Autowired
    SubscriptionPaymentMapper subscriptionPaymentMapper;
    public SubscriptionPaymentServiceImpl(SubscriptionPaymentRepository subscriptionPaymentRepository) {
        super(subscriptionPaymentRepository);
    }

    @Override
    public List<SubscriptionPaymentDTO> getAll() {
        List<SubscriptionPayment> subscriptionPayments = subscriptionPaymentRepository.findAll();
        return subscriptionPayments.stream().map(subscriptionPaymentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SubscriptionPaymentDTO get(Long id) {
        SubscriptionPayment subscriptionPayment =  getIfExistById(id);
        return subscriptionPaymentMapper.toDTO(subscriptionPayment);
    }

    @Override
    public SubscriptionPaymentDTO add(SubscriptionPaymentDTO subscriptionPaymentDTO) {

        SubscriptionPayment subscriptionPayment = subscriptionPaymentMapper.toModel(subscriptionPaymentDTO);
        subscriptionPayment = subscriptionPaymentRepository.save(subscriptionPayment);
        return subscriptionPaymentMapper.toDTO(subscriptionPayment);
    }

    @Override
    public SubscriptionPaymentDTO update(SubscriptionPaymentDTO subscriptionPaymentDTO) {
        getIfExistById(subscriptionPaymentDTO.getId());
        SubscriptionPayment subscriptionPayment = subscriptionPaymentMapper.toModel(subscriptionPaymentDTO);
        subscriptionPayment = subscriptionPaymentRepository.save(subscriptionPayment);
        return subscriptionPaymentMapper.toDTO(subscriptionPayment);
    }

    @Override
    public void delete(Long id) {
        SubscriptionPayment subscriptionPayment = getIfExistById(id);
        subscriptionPaymentRepository.delete(subscriptionPayment);
    }
}
