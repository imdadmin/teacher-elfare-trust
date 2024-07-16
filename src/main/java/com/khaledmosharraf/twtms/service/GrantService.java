package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.GrantDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.dto.UserDTO;

import java.util.List;

public interface GrantService extends CrudService<GrantDTO,Long> {
    public List<GrantDTO> getByUserId(Long id);
    public List<GrantDTO> getByUsername(String username);

    public List<GrantDTO> getAllGrants();
    public List<GrantDTO> getByDistrictId(Long districtId);
    public List<GrantDTO> getBySubDistrictId(Long subDistrictId);
    public List<GrantDTO> getByDistrictIdAndSubDistrictId(Long districtId,Long subDistrictId);
}
