package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.NextPaymentDTO;
import com.khaledmosharraf.twtms.dto.PaymentInfoDTO;
import com.khaledmosharraf.twtms.dto.YearlyfeeDTO;

public interface YearlyfeeService extends CrudService<YearlyfeeDTO,Long> {
    public YearlyfeeDTO getByYear(Integer year);

    public NextPaymentDTO getNextPaymentInfo(Integer lastPaymentYear, Integer joiningYear );
}
