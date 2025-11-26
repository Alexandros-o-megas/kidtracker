package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.UsuarioDTO;
import eda.projecto.kidtracker.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Procura todos os utilizadores na base de dados que tenham o perfil "ROLE_MOTORISTA".
     * @return Uma lista de UsuarioDTO.
     */
    public List<UsuarioDTO> findAllMotoristas() {
        // Esta é a forma mais simples. Uma query JPA seria mais eficiente.
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getRoles().stream()
                        .anyMatch(role -> role.getNome().equals("ROLE_MOTORISTA")))
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }
}