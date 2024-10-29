package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.PaymentInfoDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SubscriptionPaymentService extends CrudService<SubscriptionPaymentDTO,Long> {
    public List<SubscriptionPaymentDTO> getByUserId(Long id);
    public List<SubscriptionPaymentDTO> getByUsername(String username);

    public SubscriptionPaymentDTO getByTranId(String tranId);
    public SubscriptionPaymentDTO getByToken(String token);
    public Integer getLastPaymentYear( Long userId);
    public PaymentInfoDTO getPaymentInfo( Integer lastPaymentYear, Integer joiningYear );
    public Map<Long, PaymentInfoDTO> getLastPaymentsForUsers();

    public List<SubscriptionPaymentDTO> getAllSubscriptionPaymentsByDates(LocalDateTime fromDate, LocalDateTime toDate);

    public List<SubscriptionPaymentDTO> getByDistrictIdByDates(Long districtId,LocalDateTime fromDate,LocalDateTime toDate);
    public List<SubscriptionPaymentDTO> getBySubDistrictIdByDates(Long subDistrictId,LocalDateTime fromDate,LocalDateTime toDate);
    public List<SubscriptionPaymentDTO> getByDistrictIdAndSubDistrictIdByDates(Long districtId,Long subDistrictId,LocalDateTime fromDate,LocalDateTime toDate);


}
