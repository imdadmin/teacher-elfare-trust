package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.SubscriptionPaymentDTO;
import com.khaledmosharraf.twtms.dto.SubscriptionPaymentRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionPaymentRequestMapper {
    @Mapping(source = "userId", target = "user.id")
    SubscriptionPaymentDTO toSubscriptionPaymentDTO(SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO);
    @Mapping(source = "user.id", target = "userId")
    SubscriptionPaymentRequestDTO toSubscriptionPaymentRequestDTO(SubscriptionPaymentDTO subscriptionPaymentDTO);

}
