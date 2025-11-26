package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.Veiculo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoSimplesDTO {
    private Long id;
    private String matricula;
    private String modelo;

    public static VeiculoSimplesDTO fromEntity(Veiculo veiculo) {
        if (veiculo == null) return null;

        return VeiculoSimplesDTO.builder()
                .id(veiculo.getId())
                .matricula(veiculo.getMatricula())
                .modelo(veiculo.getModelo())
                .build();
    }
}