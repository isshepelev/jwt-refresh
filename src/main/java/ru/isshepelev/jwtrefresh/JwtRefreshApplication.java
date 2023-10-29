package ru.isshepelev.jwtrefresh;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.isshepelev.jwtrefresh.domain.Role;
import ru.isshepelev.jwtrefresh.domain.User;
import ru.isshepelev.jwtrefresh.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class JwtRefreshApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtRefreshApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null, "Ilya", "TaRaTaTA", "123", new ArrayList<>()));
            userService.saveUser(new User(null, "Dima", "Dima2004", "123", new ArrayList<>()));
            userService.saveUser(new User(null, "Sasha", "SpyKraken", "123", new ArrayList<>()));
            userService.saveUser(new User(null, "Vlad", "Ge-force", "123", new ArrayList<>()));

            userService.addRoleToUser("TaRaTaTA", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("TaRaTaTA", "ROLE_ADMIN");
            userService.addRoleToUser("TaRaTaTA", "ROLE_MANAGER");
            userService.addRoleToUser("TaRaTaTA", "ROLE_USER");
            userService.addRoleToUser("Dima2004", "ROLE_ADMIN");
            userService.addRoleToUser("SpyKraken", "ROLE_USER");
            userService.addRoleToUser("Ge-force", "ROLE_MANAGER");
        };
    }
}
