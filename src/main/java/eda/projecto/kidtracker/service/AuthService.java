package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.*;
import eda.projecto.kidtracker.model.Role;
import eda.projecto.kidtracker.model.Usuario;
import eda.projecto.kidtracker.repository.RoleRepository;
import eda.projecto.kidtracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; // Necessário para a lógica de login
    private final JwtService jwtService; // Supondo que tenhas um serviço para JWT

    /**
     * Autentica um utilizador e gera um token JWT.
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var userEntity = usuarioRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Email ou senha invalidos!"));

        var jwtToken = jwtService.generateToken(userEntity);

        var userDto = UsuarioDTO.fromEntity(userEntity);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(userDto)
                .build();
    }

    /**
     * Regista um novo utilizador com o perfil de ENCARREGADO.
     */
    @Transactional
    public void registerUser(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Erro: O email já está em uso!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        
        Role encarregadoRole = roleRepository.findByNome("ROLE_ENCARREGADO")
                .orElseThrow(() -> new RuntimeException("Erro: Role 'ROLE_ENCARREGADO' não encontrada."));
        
        usuario.setRoles(Set.of(encarregadoRole));
        usuarioRepository.save(usuario);
    }

    /**
     * Obtém o perfil do utilizador atualmente autenticado.
     */
//    public UserDetails getCurrentUserProfile() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new IllegalStateException("Nenhum utilizador autenticado.");
//        }
//        return (UserDetails) authentication.getPrincipal();
//    }
    public Usuario getCurrentUserProfile() { // MUDANÇA 1: O método agora devolve o nosso tipo `Usuario`
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Nenhum utilizador autenticado.");
        }

        Object principal = authentication.getPrincipal();

        // MUDANÇA 2: Verificamos se o objeto é uma instância da nossa classe
        if (principal instanceof Usuario) {
            return (Usuario) principal; // Fazemos o cast para o nosso tipo `Usuario`
        } else {
            // Isto pode acontecer se outro tipo de autenticação estiver ativo,
            // mas no nosso caso deve ser sempre um `Usuario`.
            throw new IllegalStateException("O principal de autenticação não é uma instância de Usuario.");
        }
    }

    @Transactional
    public void registerMotorista(CreateMotoristaRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Erro: O email já está em uso!");
        }
        Usuario motorista = new Usuario();
        motorista.setNome(request.getNome());
        motorista.setEmail(request.getEmail());
        motorista.setSenhaHash(passwordEncoder.encode(request.getSenha()));

        Role motoristaRole = roleRepository.findByNome("ROLE_MOTORISTA")
                .orElseThrow(() -> new RuntimeException("Erro: Role 'ROLE_MOTORISTA' não encontrada."));

        motorista.setRoles(Set.of(motoristaRole));
        usuarioRepository.save(motorista);
    }
}