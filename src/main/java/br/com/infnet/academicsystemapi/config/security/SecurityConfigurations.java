package br.com.infnet.academicsystemapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Desabilita a proteção CSRF por ser uma API stateless com JWT.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configura a gestão de sessão como STATELESS.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Define as regras de autorização para requisições HTTP.
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público (sem autenticação) ao endpoint de "login".
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        // Exige autenticação para todas as outras requisições.
                        .anyRequest().authenticated()
                )
                // 4. Desabilita explicitamente a proteção de "login" baseada em formulário HTML
                .formLogin(AbstractHttpConfigurer::disable)

                // 5. Desabilita a autenticação HTTP Basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // 6. Adiciona filtro personalizado ANTES do filtro padrão do Spring.
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Bean necessário para ser injetado no controller de autenticação.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Define o algoritmo de hashing de senhas a ser usado (BCrypt).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
