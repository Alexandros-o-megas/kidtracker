package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.Atividade;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AtividadeDTO {

    private Long id;
    private String tipo;
    private String texto;
    private LocalDateTime timestamp;
    private String nomeUsuario; // Apenas o nome do utilizador, não o objeto completo

    public static AtividadeDTO fromEntity(Atividade atividade) {
        return AtividadeDTO.builder()
                .id(atividade.getId())
                .tipo(atividade.getTipo())
                .texto(atividade.getTexto())
                .timestamp(atividade.getTimestamp())
                // Verificação de segurança: só adiciona o nome se o utilizador não for nulo
                .nomeUsuario(atividade.getUsuario() != null ? atividade.getUsuario().getNome() : null)
                .build();
    }
}