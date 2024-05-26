package com.khaledmosharraf.twtms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private Long id;
    @NotNull
    private Long subDistrictId;

    @NotBlank
    @Size(min = 2, max = 80, message = "{validation.size}")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "{pattern.nametype}")
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

    private String password;

    private String twoFactorSecret;

    private String twoFactorRecoveryCodes;

    private LocalDateTime twoFactorConfirmedAt;

    private String rememberToken;

    private Long currentTeamId;

    private String profilePhotoPath;

    private Boolean completed;
}
