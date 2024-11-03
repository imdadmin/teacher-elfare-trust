package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.NextPaymentDTO;
import com.khaledmosharraf.twtms.dto.YearlyfeeDTO;
import com.khaledmosharraf.twtms.mapper.YearlyfeeMapper;
import com.khaledmosharraf.twtms.model.Yearlyfee;
import com.khaledmosharraf.twtms.repository.YearlyfeeRepository;
import com.khaledmosharraf.twtms.service.YearlyfeeService;
import com.khaledmosharraf.twtms.utils.Constants;
import com.khaledmosharraf.twtms.utils.DatabaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YearlyfeeServiceImpl extends IdCheckingService<Yearlyfee,Long> implements YearlyfeeService {

    @Autowired
    YearlyfeeRepository yearlyfeeRepository;
    @Autowired
    YearlyfeeMapper yearlyfeeMapper;
    @Value("${payment.year.start}")
    private Integer startYear;
    public YearlyfeeServiceImpl(YearlyfeeRepository yearlyfeeRepository) {
        super(yearlyfeeRepository);
    }


    @Override
    public List<YearlyfeeDTO> getAll() {
        List<Yearlyfee> yearlyfees = yearlyfeeRepository.findAll(DatabaseConstants.sortById_DESC);
        return yearlyfees.stream().map(yearlyfeeMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public YearlyfeeDTO get(Long id) {
        Yearlyfee yearlyfee =  getIfExistById(id);
        return yearlyfeeMapper.toDTO(yearlyfee);
    }
    @Override
    public YearlyfeeDTO getByYear(Integer year) {
        Yearlyfee yearlyfee =  yearlyfeeRepository.findTopByYear(year);
        return yearlyfeeMapper.toDTO(yearlyfee);
    }

    @Override
    public YearlyfeeDTO add(YearlyfeeDTO yearlyfeeDTO) {

        Yearlyfee yearlyfee = yearlyfeeMapper.toModel(yearlyfeeDTO);
        yearlyfee = yearlyfeeRepository.save(yearlyfee);
        return yearlyfeeMapper.toDTO(yearlyfee);
    }

    @Override
    public YearlyfeeDTO update(YearlyfeeDTO yearlyfeeDTO) {
        getIfExistById(yearlyfeeDTO.getId());
        Yearlyfee yearlyfee = yearlyfeeMapper.toModel(yearlyfeeDTO);
        yearlyfee = yearlyfeeRepository.save(yearlyfee);
        return yearlyfeeMapper.toDTO(yearlyfee);
    }

    @Override
    public void delete(Long id) {
        Yearlyfee yearlyfee = getIfExistById(id);
        yearlyfeeRepository.delete(yearlyfee);
    }

    @Override
    public NextPaymentDTO getNextPaymentInfo(Integer lastPaymentYear, Integer joiningYear) {

        Integer paymentYear =  lastPaymentYear;
        if(paymentYear==null){
            paymentYear = joiningYear;
            if(paymentYear==null ){
                paymentYear = startYear;
            }
            paymentYear--;
        }
        paymentYear++;
        paymentYear = Integer.max(paymentYear,startYear);
        Double paymentAmount = 0.0;

        YearlyfeeDTO yearlyfeeDTO = getByYear(paymentYear);
        if(yearlyfeeDTO==null){
            paymentYear=-1;
        }
        else {
            paymentAmount = yearlyfeeDTO.getAmount();
        }
        NextPaymentDTO nextPaymentDTO = new NextPaymentDTO(paymentYear,paymentAmount);
        return nextPaymentDTO;
    }
}
