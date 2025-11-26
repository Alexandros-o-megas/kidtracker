package eda.projecto.kidtracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViagemInfoDTO {
    private Long veiculoId;
    private String matriculaVeiculo;
    private double latAtual;
    private double lonAtual;
}