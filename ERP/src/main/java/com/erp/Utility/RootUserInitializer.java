package com.erp.Utility;

import com.erp.Model.RootUser;
import com.erp.Repository.Rootuser.RootUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RootUserInitializer implements CommandLineRunner {

    @Autowired
    private RootUserRepository rootUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${root.email}")
    private String email;

    @Value("${root.password}")
    private String password;

    @Value("${root.name}")
    private String name;

    @Override
    public void run(String... args) throws Exception {
        // Check if the root user exists
        if (!rootUserRepository.existsByEmail(email)) {
            RootUser rootUser = new RootUser();
            rootUser.setEmail(email);
            rootUser.setName(name);
            rootUser.setPassword(passwordEncoder.encode(password));
            rootUserRepository.save(rootUser);
            System.out.println("✅ Root user created.");
        }
    }
}
