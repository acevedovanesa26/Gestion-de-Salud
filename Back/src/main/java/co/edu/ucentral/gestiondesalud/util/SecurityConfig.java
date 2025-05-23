package co.edu.ucentral.gestiondesalud.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 🔓 Permitir todas las peticiones
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // 🔒 Desactiva httpBasic auth

        return http.build();
    }
}
