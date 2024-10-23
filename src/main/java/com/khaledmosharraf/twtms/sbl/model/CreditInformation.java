package com.khaledmosharraf.twtms.sbl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class CreditInformation {
    @JsonProperty("SerialNo")
    private int serialNo;

    @JsonProperty("CrAccountOrChallanNo")
    private String crAccountOrChallanNo;

    @JsonProperty("CrAmount")
    private Double crAmount;

    @JsonProperty("TranMode")
    private String tranMode;

    @JsonProperty("Onbehalf")
    private String onbehalf;

}
