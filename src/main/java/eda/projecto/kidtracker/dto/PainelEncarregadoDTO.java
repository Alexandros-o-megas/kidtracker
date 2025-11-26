package eda.projecto.kidtracker.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PainelEncarregadoDTO {
    private String nomeEncarregado;
    private List<AlunoViagemDTO> alunos;
}