package com.khaledmosharraf.twtms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankDTO {
    private Long id;
    private String name;
    private String account_no;
}
