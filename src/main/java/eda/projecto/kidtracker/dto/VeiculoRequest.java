package eda.projecto.kidtracker.dto;

import lombok.Data;

/**
 * DTO para receber dados de criação ou atualização de um Veículo a partir do frontend.
 */
@Data
public class VeiculoRequest {

    private String matricula;
    private String modelo;
    private Integer capacidade;
    private String status;
    private Long motoristaId; // Recebemos apenas o ID do motorista

}
