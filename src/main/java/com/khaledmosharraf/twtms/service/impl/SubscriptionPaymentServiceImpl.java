package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.PaymentInfoDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentMapper;
import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.SubscriptionPaymentRepository;
import com.khaledmosharraf.twtms.repository.UserRepository;
import com.khaledmosharraf.twtms.service.SubscriptionPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubscriptionPaymentServiceImpl extends IdCheckingService<SubscriptionPayment,Long> implements SubscriptionPaymentService {

    @Autowired
    SubscriptionPaymentRepository subscriptionPaymentRepository;
    @Autowired
    SubscriptionPaymentMapper subscriptionPaymentMapper;
    @Autowired
    UserRepository userRepository;
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

    @Override
    public List<SubscriptionPaymentDTO> getByUserId(Long id) {
        List<SubscriptionPayment> subscriptionPayments = subscriptionPaymentRepository.findByUserIdOrderByPaymentDateDesc(id);
        return subscriptionPayments.stream().map(subscriptionPaymentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionPaymentDTO> getByUsername(String username) {
        List<SubscriptionPayment> subscriptionPayments = subscriptionPaymentRepository.findByUsername(username);
        return subscriptionPayments.stream().map(subscriptionPaymentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SubscriptionPaymentDTO getByTranId(String tranId) {
        SubscriptionPayment subscriptionPayment =  subscriptionPaymentRepository.findFirstByTranId(tranId);
        return subscriptionPaymentMapper.toDTO(subscriptionPayment);
    }
    @Override
    public SubscriptionPaymentDTO getByToken(String token) {
        SubscriptionPayment subscriptionPayment =  subscriptionPaymentRepository.findFirstByToken(token);
        return subscriptionPaymentMapper.toDTO(subscriptionPayment);
    }

    @Override
    public Integer getLastPaymentYear( Long userId ) {
        return subscriptionPaymentRepository.findLastPaymentYear(userId);
    }
    @Override
    public PaymentInfoDTO getPaymentInfo( Integer lastPaymentYear, Integer joiningYear ) {
        PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
        Integer tempLastPaymentYear = lastPaymentYear;
        if (lastPaymentYear == null) {
            tempLastPaymentYear = joiningYear;
        }
        int currentYear = LocalDate.now().getYear();
        paymentInfo.setLastPaymentYear(lastPaymentYear);
        paymentInfo.setDueYears(currentYear-tempLastPaymentYear);

        return paymentInfo;
    }

    @Override

    public Map<Long, PaymentInfoDTO> getLastPaymentsForUsers() {
        List<User> users = userRepository.findAllTeacher();
        Map<Long, PaymentInfoDTO> paymentInfoMap = new HashMap<>();

        for (User user : users) {
            Integer lastPayment = subscriptionPaymentRepository.findLastPaymentYear(user.getId());
            PaymentInfoDTO paymentInfo = getPaymentInfo(lastPayment,user.getJoiningDate().getYear());
            paymentInfoMap.put(user.getId(), paymentInfo);
        }
        return paymentInfoMap;
    }


    public List<SubscriptionPaymentDTO> getAllSubscriptionPaymentsByDates(LocalDateTime fromDate, LocalDateTime toDate) {
        return subscriptionPaymentRepository.findAllSubscriptionPaymentsByDates(fromDate, toDate)
                .stream().map(subscriptionPaymentMapper::toDTO).toList();
    }

    public List<SubscriptionPaymentDTO> getByDistrictIdByDates(Long districtId, LocalDateTime fromDate, LocalDateTime toDate) {
        return subscriptionPaymentRepository.findByDistrictIdByDates(districtId, fromDate, toDate)
                .stream().map(subscriptionPaymentMapper::toDTO).toList();
    }

    public List<SubscriptionPaymentDTO> getBySubDistrictIdByDates(Long subDistrictId, LocalDateTime fromDate, LocalDateTime toDate) {
        return subscriptionPaymentRepository.findBySubDistrictIdByDates(subDistrictId, fromDate, toDate)
                .stream().map(subscriptionPaymentMapper::toDTO).toList();
    }

    public List<SubscriptionPaymentDTO> getByDistrictIdAndSubDistrictIdByDates(Long districtId, Long subDistrictId, LocalDateTime fromDate, LocalDateTime toDate) {
        return subscriptionPaymentRepository.findByDistrictIdAndSubDistrictIdByDates(districtId, subDistrictId, fromDate, toDate)
                .stream().map(subscriptionPaymentMapper::toDTO).toList();
    }

}
