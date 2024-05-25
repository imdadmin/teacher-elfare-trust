package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "sub_districts")
public class SubDistrict extends Autditable {

    @Id
    @GeneratedValue
    private Long id;
    private String eName;
    private String bName;
    @ManyToOne
    @JoinColumn(name = "district_id")
    District district;

    @OneToMany(mappedBy = "subDistrict", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> users;

}
