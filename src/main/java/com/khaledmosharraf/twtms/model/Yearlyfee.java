package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yearlyfees")
public class Yearlyfee extends Autditable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yearlyfee_sequence")
    @SequenceGenerator(name = "yearlyfee_sequence", sequenceName = "YEARLYFEE_SEQUENCE", allocationSize = 1)
    private Long id;
    private Integer year;
    private Double amount;
    private String details;
}
