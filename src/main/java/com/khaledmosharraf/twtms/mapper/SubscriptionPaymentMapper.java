package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.model.SubscriptionPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionPaymentMapper {
    SubscriptionPayment toModel(SubscriptionPaymentDTO subscriptionPaymentDTO);
    SubscriptionPaymentDTO toDTO(SubscriptionPayment subscriptionPayment);

}