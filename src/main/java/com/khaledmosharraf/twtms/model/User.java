package com.khaledmosharraf.twtms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Table(name = "users")
public class User extends Autditable{

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
        @SequenceGenerator(name = "user_sequence", sequenceName = "USER_SEQUENCE", allocationSize = 1)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sub_district_id")
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
        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
        @Column(name = "authority")
        private Set<String> roles;

        private String twoFactorSecret;

        private String twoFactorRecoveryCodes;

        private LocalDateTime twoFactorConfirmedAt;

        private String rememberToken;

        private Long currentTeamId;

        private String profilePhotoPath;

        private Boolean completed;


        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Grant> grants;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<SubscriptionPayment> subscriptionPayments;

        public User() {
                this.grants = new ArrayList<>();
                this.subscriptionPayments = new ArrayList<>();
                this.roles = new HashSet<>();
        }
}
