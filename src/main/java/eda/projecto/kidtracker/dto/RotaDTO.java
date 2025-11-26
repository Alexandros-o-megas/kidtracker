package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.Rota;
import lombok.Builder;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RotaDTO {
    private Long id;
    private String nome;
    private List<ParagemRotaDTO> paragens;

    public static RotaDTO fromEntity(Rota rota) {
        if (rota == null) return null;

        List<ParagemRotaDTO> paragensDto = rota.getParagens().stream()
                // A ordenação já é garantida pelo @OrderBy na entidade, mas isto é uma dupla segurança.
                .sorted(Comparator.comparing(paragem -> paragem.getSequencia()))
                .map(ParagemRotaDTO::fromEntity)
                .collect(Collectors.toList());

        return RotaDTO.builder()
                .id(rota.getId())
                .nome(rota.getNome())
                .paragens(paragensDto)
                .build();
    }
}