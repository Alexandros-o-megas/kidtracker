package eda.projecto.kidtracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite pedidos vindos da tua aplicação React
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite todos os cabeçalhos comuns (Authorization, Content-Type, etc.)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Necessário para autenticação baseada em cookies/sessões, mas bom ter
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica esta configuração a todos os caminhos da nossa API
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
