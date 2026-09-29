package br.com.osmanager.osmanager_backend.config;

import br.com.osmanager.osmanager_backend.entity.Role;
import br.com.osmanager.osmanager_backend.entity.User;
import br.com.osmanager.osmanager_backend.repository.RoleRepository;
import br.com.osmanager.osmanager_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() ->
                            roleRepository.save(new Role("USER"))
                    );

            Role technicianRole = roleRepository.findByName("TECHNICIAN")
                    .orElseGet(() ->
                            roleRepository.save(new Role("TECHNICIAN"))
                    );

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() ->
                            roleRepository.save(new Role("ADMIN"))
                    );

            if (userRepository.findByEmail("admin@osmanager.com").isEmpty()) {

                User admin = new User();

                admin.setName("Administrador");
                admin.setEmail("admin@osmanager.com");
                admin.setPassword(
                        passwordEncoder.encode("Admin@123")
                );
                admin.setActive(true);
                admin.setRole(adminRole);

                userRepository.save(admin);
            }

            if (userRepository.findByEmail("user@osmanager.com").isEmpty()) {
                User user = new User();

                user.setName("Usuário Teste");
                user.setEmail("user@osmanager.com");
                user.setPassword(passwordEncoder.encode("User@123"));
                user.setActive(true);
                user.setRole(userRole);

                userRepository.save(user);
            }

            if (userRepository.findByEmail("tech@osmanager.com").isEmpty()) {
                User technician = new User();

                technician.setName("Técnico Teste");
                technician.setEmail("tech@osmanager.com");
                technician.setPassword(
                        passwordEncoder.encode("Tech@123")
                );
                technician.setActive(true);
                technician.setRole(technicianRole);

                userRepository.save(technician);
            }
        };
    }
}