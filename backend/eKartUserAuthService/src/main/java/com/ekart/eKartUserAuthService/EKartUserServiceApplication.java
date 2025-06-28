package com.ekart.eKartUserAuthService;

import com.ekart.eKartUserAuthService.model.Roles;
import com.ekart.eKartUserAuthService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class EKartUserServiceApplication implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(EKartUserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Roles> rolesList = roleRepository.findAll();
        if (rolesList.isEmpty()) {
            Roles role1 = new Roles("ROLE_ADMIN");
            Roles role2 = new Roles("ROLE_USER");
            Roles role3 = new Roles("ROLE_SELLER");
            Roles role4 = new Roles("ROLE_CUSTOMER");
            roleRepository.saveAll(List.of(role1, role2, role3, role4));
        }
    }
}
