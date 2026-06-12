package com.web.mycar.SpringWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
// IMPORTANTE: Adicione esta linha abaixo para o logout funcionar com link simples
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; 

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/css/**", "/js/**", "/vendor/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .usernameParameter("cpf")
                .passwordParameter("senha")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            // AQUI ESTÁ A MÁGICA DO LOGOUT
            .logout((logout) -> logout
                // Permite usar um link <a href="/logout"> (método GET) em vez de um form POST
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
                .logoutSuccessUrl("/login?logout") // Volta pro login com mensagem
                .invalidateHttpSession(true) // Destrói a sessão no servidor
                .deleteCookies("JSESSIONID") // Apaga o cookie no navegador
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        @SuppressWarnings("deprecation")
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("71466783174")
                .password("02012004")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}