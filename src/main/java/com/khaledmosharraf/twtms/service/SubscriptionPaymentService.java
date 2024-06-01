package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;

import java.util.List;

public interface SubscriptionPaymentService extends CrudService<SubscriptionPaymentDTO,Long> {
    public List<SubscriptionPaymentDTO> getByUserId(Long id);
    public List<SubscriptionPaymentDTO> getByUsername(String username);

}
