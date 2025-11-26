package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.Role;
import eda.projecto.kidtracker.model.Usuario;
import lombok.Builder;
import lombok.Data;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UsuarioDTO {

    // ===== CAMPOS ESSENCIAIS =====
    private Long id;
    private String nome;
    private String email;

    // ===== INFORMAÇÕES DE AUTORIZAÇÃO =====
    // É crucial que o frontend saiba quais são os perfis do utilizador
    // para poder mostrar/esconder botões e permitir acesso a rotas.
    private Set<String> roles;

    /**
     * Método de fábrica estático (factory method) para converter a nossa entidade Usuario
     * num DTO seguro e limpo para ser enviado pela API.
     *
     * @param usuario A entidade Usuario vinda da base de dados.
     * @return Um UsuarioDTO contendo apenas os dados públicos.
     */
    public static UsuarioDTO fromEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        // Converte o Set<Role> para um Set<String> (ex: ["ROLE_ADMIN", "ROLE_MOTORISTA"])
        Set<String> roleNames = usuario.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toSet());

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .roles(roleNames)
                .build();
    }
}