package eda.projecto.kidtracker.dto;

import eda.projecto.kidtracker.model.Viagem;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViagemDTO {

    private Long id;
    private String status;
    private LocalDateTime inicioPrevisto;

    // Informações da Rota
    private RotaDTO rota;

    // Informações do Veículo
    private VeiculoSimplesDTO veiculo;

    /**
     * Factory method para converter a entidade Viagem para um DTO.
     */
    public static ViagemDTO fromEntity(Viagem viagem) {
        if (viagem == null) {
            return null;
        }

        return ViagemDTO.builder()
                .id(viagem.getId())
                .status(viagem.getStatus().toString())
                .inicioPrevisto(viagem.getInicioPrevisto())
                .rota(RotaDTO.fromEntity(viagem.getRota()))
                .veiculo(VeiculoSimplesDTO.fromEntity(viagem.getVeiculo()))
                .build();
    }
}