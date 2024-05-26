package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sub_districts")
public class SubDistrict extends Autditable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_district_sequence")
    @SequenceGenerator(name = "sub_district_sequence", sequenceName = "SUB_DISTRICT_SEQUENCE", allocationSize = 1)
    private Long id;
    private String eName;
    private String bName;
    @ManyToOne
    @JoinColumn(name = "district_id")
    District district;


}
