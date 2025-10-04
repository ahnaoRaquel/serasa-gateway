package com.gateway.serasa.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("Admin@123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("User@123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.PUT, "/api/documento/*/ativar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/documento/*/desativar").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/consulta/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST, "/api/consulta/lote").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST, "/api/consulta/nome").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST, "/api/consulta/restricoes").hasAnyRole("ADMIN","USER")

                        .requestMatchers(HttpMethod.GET, "/api/historico/consultas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/historico/consultas/resumo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/historico/consultas/periodo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/historico/consultas/resumo/periodo").hasRole("ADMIN")

                        .anyRequest().authenticated()
                );

        return http.build();
    }
}