package eda.projecto.kidtracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoViagemDTO {
    private String nomeAluno;
    private String nomeRota;
    private PontoParagemDTO pontoParagem;
    private ViagemInfoDTO viagem;
}