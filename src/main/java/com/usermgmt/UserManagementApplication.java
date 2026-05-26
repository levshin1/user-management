package com.usermgmt;

import com.usermgmt.entity.User;
import com.usermgmt.enums.Role;
import com.usermgmt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("Alice", "Johnson", "alice@example.com", Role.ADMIN, "555-0101"));
            userRepository.save(new User("Bob", "Smith", "bob@example.com", Role.MANAGER, "555-0102"));
            userRepository.save(new User("Carol", "Williams", "carol@example.com", Role.EMPLOYEE, "555-0103"));
            userRepository.save(new User("David", "Brown", "david@example.com", Role.EMPLOYEE, "555-0104"));
            userRepository.save(new User("Eve", "Davis", "eve@example.com", Role.MANAGER, "555-0105"));
        };
    }
}
