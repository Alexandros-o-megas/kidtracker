package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.PontoParagem;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PontoParagemDTO {
    private Long id;
    private String nome;
    private double lat;
    private double lon;
    private List<AlunoSimplesDTO> alunos; // Informação dos alunos na paragem

    public static PontoParagemDTO fromEntity(PontoParagem paragem) {
        if (paragem == null) return null;

        List<AlunoSimplesDTO> alunosDto = paragem.getAlunos().stream()
                .map(AlunoSimplesDTO::fromEntity)
                .collect(Collectors.toList());

        return PontoParagemDTO.builder()
                .id(paragem.getId())
                .nome(paragem.getNome())
                .lat(paragem.getLat())
                .lon(paragem.getLon())
                .alunos(alunosDto)
                .build();
    }
}