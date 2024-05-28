package com.khaledmosharraf.twtms.initializer;

import com.khaledmosharraf.twtms.model.SubDistrict;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.DistrictRepository;
import com.khaledmosharraf.twtms.repository.SubDistrictRepository;
import com.khaledmosharraf.twtms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@Order(3)
public class UserInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private SubDistrictRepository subDistrictRepository;
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            // Create a new admin user
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");

            String defaultPassword = "password";
            String hashedPassword = passwordEncoder.encode(defaultPassword);
            adminUser.setPassword(hashedPassword);

            // Set admin role
            Set<String> roles = new HashSet<>();
            roles.add("ADMIN");
            adminUser.setRoles(roles);
            SubDistrict subDistrict = subDistrictRepository.findTopByOrderByIdAsc().orElse(null);
            adminUser.setSubDistrict(subDistrict);

            userRepository.save(adminUser);
        }

    }
}
