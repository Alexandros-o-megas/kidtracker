package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.DashboardStatsDTO;
import eda.projecto.kidtracker.repository.*; // Importa todos os teus repositórios
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final RotaRepository rotaRepository;
    // Adicione outros repositórios conforme necessário

    public DashboardStatsDTO getDashboardStats() {
        long totalVeiculos = veiculoRepository.count();
        // A lógica aqui deve contar apenas motoristas. Ex: usuarioRepository.countByRole("ROLE_MOTORISTA")
        long totalMotoristas = usuarioRepository.count(); 
        long totalAlunos = alunoRepository.count();
        long rotasAtivas = rotaRepository.count(); // A lógica de "ativa" pode ser mais complexa

        return DashboardStatsDTO.builder()
            .totalVeiculos(totalVeiculos)
            .totalMotoristas(totalMotoristas)
            .totalAlunos(totalAlunos)
            .rotasAtivas((int) rotasAtivas)
            .alertas(5) // Placeholder
            .pontualidade(98) // Placeholder
            .build();
    }
}