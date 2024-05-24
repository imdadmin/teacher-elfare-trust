package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SubDistrict extends Autditable {

    @Id
    @GeneratedValue
    private Long id;
    private String eName;
    private String bName;
    @ManyToOne
    @JoinColumn(name = "district_id")
    District district;

}
