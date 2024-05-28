package com.khaledmosharraf.twtms.dto;

import com.khaledmosharraf.twtms.model.SubDistrict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private SubDistrict subDistrict;

    private String name;

    private String uniId;

    private String nid;

    private String bloodGroup;

    private String phone;

    private String fatherName;

    private String motherName;

    private String spouseName;

    private String presentAddress;

    private String permanentAddress;

    private LocalDate joiningDate;

    private LocalDate prlDate;

    private String designation;

    private String schoolName;

    private LocalDate dateOfBirth;

    private Double payscale;

    private Double totalSalaryWithdraw;

    private String email;

    private LocalDate emailVerifiedAt;

    private String username;

    private String password;

    private Set<String> roles;

    private String twoFactorSecret;

    private String twoFactorRecoveryCodes;

    private LocalDateTime twoFactorConfirmedAt;

    private String rememberToken;

    private Long currentTeamId;

    private String profilePhotoPath;

    private Boolean completed;
}
