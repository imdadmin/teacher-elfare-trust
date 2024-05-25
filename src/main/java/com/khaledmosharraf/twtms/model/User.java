package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "users")
public class User extends Autditable{

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
        @SequenceGenerator(name = "user_sequence", sequenceName = "USER_SEQUENCE", allocationSize = 1)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "sub_district_id")
        private SubDistrict subDistrict;

        private String name;

        private String uid;

        private String nid;

        @Column(name = "blood_group")
        private String bloodGroup;

        private String phone;

        @Column(name = "father_name")
        private String fatherName;

        @Column(name = "mother_name")
        private String motherName;

        @Column(name = "spouse_name")
        private String spouseName;

        @Column(name = "present_address")
        private String presentAddress;

        @Column(name = "permanent_address")
        private String permanentAddress;

        @Column(name = "joining_date")
        private String joiningDate;

        @Column(name = "prl_date")
        private String prlDate;

        private String designation;

        @Column(name = "school_name")
        private String schoolName;

        @Column(name = "date_of_birth")
        private Date dateOfBirth;

        private String payscale;

        @Column(name = "total_salary_withdraw")
        private String totalSalaryWithdraw;

        private String email;

        @Column(name = "email_verified_at")
        private Date emailVerifiedAt;

        private String password;

        @Column(name = "two_factor_secret", columnDefinition = "text")
        private String twoFactorSecret;

        @Column(name = "two_factor_recovery_codes", columnDefinition = "text")
        private String twoFactorRecoveryCodes;

        @Column(name = "two_factor_confirmed_at")
        private LocalDateTime twoFactorConfirmedAt;

        @Column(name = "remember_token")
        private String rememberToken;

        @Column(name = "current_team_id")
        private Long currentTeamId;

        @Column(name = "profile_photo_path")
        private String profilePhotoPath;

        private Boolean completed;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Grant> grants;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<SubscriptionPayment> subscriptionPayments;

}
