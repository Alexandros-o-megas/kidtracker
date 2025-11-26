package eda.projecto.kidtracker.dto;
import eda.projecto.kidtracker.model.Aluno;
import lombok.Builder;
import lombok.Data;
@Data @Builder
public class AlunoSimplesDTO {
    private Long id;
    private String nome;

    public static AlunoSimplesDTO fromEntity(Aluno aluno) {
        return AlunoSimplesDTO.builder().id(aluno.getId()).nome(aluno.getNome()).build();
    }
}