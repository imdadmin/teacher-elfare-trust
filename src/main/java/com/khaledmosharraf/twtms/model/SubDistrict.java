package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class SubDistrict extends Autditable {

    @Id
    @GeneratedValue
    private Long id;
    private String e_name;
    private String b_name;
    @ManyToOne
    @JoinColumn(name = "district_id")
    District district;

    public SubDistrict() {

    }
}
