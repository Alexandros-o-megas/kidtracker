package eda.projecto.kidtracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa o BCrypt para codificar as senhas. É o padrão da indústria.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desativar CSRF (Cross-Site Request Forgery), pois não usamos sessões/cookies
            .csrf(csrf -> csrf.disable())

            // 2. Definir a gestão da sessão como STATELESS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 3. Configurar as regras de autorização para os endpoints
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos: login, registo, e o endpoint do WebSocket
                .requestMatchers("/api/auth/**", "/ws/**").permitAll()
                // Todos os outros endpoints exigem autenticação
                .anyRequest().authenticated()
            );
        
        // NOTA: Mais tarde, iremos adicionar aqui o filtro para validar o token JWT em cada pedido.
        
        return http.build();
    }
}