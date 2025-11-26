package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.Aluno;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AlunoDTO {
    private Long id;
    private String nome;
    private String matricula;
    private String nomeFamilia;
    private String nomePontoParagem;

    public static AlunoDTO fromEntity(Aluno aluno) {
        return AlunoDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .matricula(aluno.getMatricula())
                .nomeFamilia(aluno.getFamilia() != null ? aluno.getFamilia().getNome() : "N/A")
                .nomePontoParagem(aluno.getPontoParagem() != null ? aluno.getPontoParagem().getNome() : "N/A")
                .build();
    }
}