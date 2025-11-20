package eda.projecto.kidtracker.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para transportar os dados agregados para o painel de administração (Dashboard).
 */
@Data     // Gera getters, setters, toString, etc.
@Builder  // Permite uma construção fluente do objeto. Ex: DashboardStatsDTO.builder().totalVeiculos(10).build();
public class DashboardStatsDTO {

    private long totalVeiculos;
    private long totalMotoristas;
    private long totalAlunos;
    private int rotasAtivas;
    private int alertas;
    private int pontualidade; // Representado como uma percentagem (ex: 98 para 98%)

}