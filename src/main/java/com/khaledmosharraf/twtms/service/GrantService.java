package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;

import java.util.List;

public interface GrantService extends CrudService<GrantDTO,Long> {
    public List<GrantDTO> getByUserId(Long id);
    public List<GrantDTO> getByUsername(String username);
}
