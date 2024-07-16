package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.PaymentInfoDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;

import java.util.List;
import java.util.Map;

public interface SubscriptionPaymentService extends CrudService<SubscriptionPaymentDTO,Long> {
    public List<SubscriptionPaymentDTO> getByUserId(Long id);
    public List<SubscriptionPaymentDTO> getByUsername(String username);
    public Integer getLastPaymentYear( Long userId);
    public PaymentInfoDTO getPaymentInfo( Integer lastPaymentYear, Integer joiningYear );
    public Map<Long, PaymentInfoDTO> getLastPaymentsForUsers();

}
