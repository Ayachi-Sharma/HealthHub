package com.medipay.config;

import com.medipay.entity.Role;
import com.medipay.repository.RoleRepository;
import com.medipay.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        initializeRoles();
    }

    private void initializeRoles() {
        // Create ROLE_ADMIN if not exists
        if (!roleRepository.existsByName(Constants.ROLE_ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(Constants.ROLE_ADMIN);
            roleRepository.save(adminRole);
            System.out.println("✅ Created ROLE_ADMIN");
        }

        // Create ROLE_DOCTOR if not exists
        if (!roleRepository.existsByName(Constants.ROLE_DOCTOR)) {
            Role doctorRole = new Role();
            doctorRole.setName(Constants.ROLE_DOCTOR);
            roleRepository.save(doctorRole);
            System.out.println("✅ Created ROLE_DOCTOR");
        }

        // Create ROLE_PATIENT if not exists
        if (!roleRepository.existsByName(Constants.ROLE_PATIENT)) {
            Role patientRole = new Role();
            patientRole.setName(Constants.ROLE_PATIENT);
            roleRepository.save(patientRole);
            System.out.println("✅ Created ROLE_PATIENT");
        }

        System.out.println("🎯 Role initialization complete!");
    }
}
