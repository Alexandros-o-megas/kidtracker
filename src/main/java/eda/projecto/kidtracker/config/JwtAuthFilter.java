package eda.projecto.kidtracker.config; // Ou o seu pacote de filtros

import eda.projecto.kidtracker.service.JwtService;
import eda.projecto.kidtracker.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Verificar se o cabeçalho de autorização existe e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Se não, passa o pedido para o próximo filtro
            return;
        }

        // 2. Extrair o token do cabeçalho
        jwt = authHeader.substring(7); // Remove o "Bearer "
        userEmail = jwtService.extractUsername(jwt); // Usa o JwtService para extrair o email do token

        // 3. Validar o token e o utilizador
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carrega os detalhes do utilizador a partir da base de dados
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Verifica se o token é válido
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Se o token for válido, cria um objeto de autenticação
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credenciais (senha) são nulas, pois já estamos a usar token
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ATUALIZA O CONTEXTO DE SEGURANÇA
                // Isto é o que diz ao Spring Security: "Este utilizador está autenticado!"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passa o pedido (e a resposta) para o próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }
}