package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.RotaPontoParagem;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ParagemRotaDTO {
    private PontoParagemDTO pontoParagem; // Reutilizamos o DTO que já definimos antes
    private int sequencia;

    public static ParagemRotaDTO fromEntity(RotaPontoParagem rotaPontoParagem) {
        if (rotaPontoParagem == null) return null;

        // É crucial popular a lista de alunos no PontoParagemDTO
        PontoParagemDTO paragemDto = PontoParagemDTO.fromEntity(rotaPontoParagem.getPontoParagem());

        return ParagemRotaDTO.builder()
                .pontoParagem(paragemDto)
                .sequencia(rotaPontoParagem.getSequencia())
                .build();
    }
}