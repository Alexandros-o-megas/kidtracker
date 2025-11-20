package eda.projecto.kidtracker.dto;


import eda.projecto.kidtracker.model.Veiculo;
import lombok.Data;

/**
 * DTO para representar os dados de um Veículo nas respostas da API.
 */
@Data
public class VeiculoDTO {

    private Long id;
    private String matricula;
    private String modelo;
    private Integer capacidade;
    private String status;
    private MotoristaDTO motorista;

    // Sub-classe estática para representar os dados essenciais do motorista
    @Data
    public static class MotoristaDTO {
        private Long id;
        private String nome;
    }
    
    /**
     * Construtor de conversão: converte uma entidade Veiculo para um VeiculoDTO.
     * @param veiculo A entidade JPA a ser convertida.
     * @return um VeiculoDTO preenchido.
     */
    public static VeiculoDTO fromEntity(Veiculo veiculo) {
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(veiculo.getId());
        dto.setMatricula(veiculo.getMatricula());
        dto.setModelo(veiculo.getModelo());
        // dto.setCapacidade(veiculo.getCapacidade()); // Descomentar quando adicionar na entidade
        // dto.setStatus(veiculo.getStatus());       // Descomentar quando adicionar na entidade

        if (veiculo.getMotorista() != null) {
            MotoristaDTO motoristaDto = new MotoristaDTO();
            motoristaDto.setId(veiculo.getMotorista().getId());
            motoristaDto.setNome(veiculo.getMotorista().getNome());
            dto.setMotorista(motoristaDto);
        }

        return dto;
    }
}
