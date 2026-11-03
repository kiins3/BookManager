package com.bookmanager.Config;

import com.bookmanager.Models.User;
import com.bookmanager.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Creates one account per role on an empty database so the API can be tried
 * right away. Turn it off with SEED_DEMO_USERS=false.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DemoDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.enabled:true}")
    private boolean enabled;

    @Value("${app.seed.password}")
    private String demoPassword;

    @Override
    public void run(String... args) {
        if (!enabled) {
            return;
        }
        createIfMissing("admin", "Demo Admin", "admin@bookmanager.local", "ADMIN");
        createIfMissing("librarian", "Demo Librarian", "librarian@bookmanager.local", "LIBRARIAN");
        createIfMissing("reader", "Demo Reader", "reader@bookmanager.local", "USER");
    }

    private void createIfMissing(String username, String name, String email, String role) {
        if (userRepository.existsByUsername(username)) {
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setNameUnsigned(name.toLowerCase());
        user.setEmail(email);
        user.setRole(role);
        user.setStatus("ACTIVE");
        user.setPassword(passwordEncoder.encode(demoPassword));
        userRepository.save(user);
        log.info("Created demo account '{}' with role {}", username, role);
    }
}
