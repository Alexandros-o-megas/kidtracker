package eda.projecto.kidtracker.dto;

import lombok.AllArgsConstructor; // 1. Adicionar import
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;  // 2. Adicionar import

/**
 * DTO para transportar os dados agregados para o painel de administração (Dashboard).
 */
@Data
@Builder
@NoArgsConstructor    // 3. Adicionar esta anotação
@AllArgsConstructor   // 4. Adicionar esta anotação
public class DashboardStatsDTO {

    private long totalVeiculos;
    private long totalMotoristas;
    private long totalAlunos;
    private int rotasAtivas;
    private int alertas;
    private int pontualidade; // Representado como uma percentagem (ex: 98 para 98%)

}