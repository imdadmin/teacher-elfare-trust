package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class District extends Autditable{

    @Id
    @GeneratedValue
    private Long id;
    private String e_name;
    private String b_name;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<SubDistrict> subDistricts ;
    public District() {
        this.subDistricts = new ArrayList<>();
    }

}
