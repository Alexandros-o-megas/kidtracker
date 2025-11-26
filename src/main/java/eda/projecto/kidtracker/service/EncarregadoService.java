package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.*; // Importa todos os DTOs que criámos
import eda.projecto.kidtracker.model.*;
import eda.projecto.kidtracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EncarregadoService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    // Precisaremos de uma forma de encontrar a família do encarregado e a localização atual da viagem

    public PainelEncarregadoDTO getPainelInfo() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario encarregado = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Encarregado não encontrado."));

        if (encarregado.getFamilia() == null) {
            throw new RuntimeException("Utilizador não está associado a nenhuma família.");
        }

        List<Aluno> alunosDaFamilia = alunoRepository.findByFamiliaId(encarregado.getFamilia().getId());

        List<AlunoViagemDTO> alunosViagem = alunosDaFamilia.stream().map(aluno -> {
            PontoParagem paragem = aluno.getPontoParagem();

            // Lógica para encontrar a rota e o veículo do aluno...
            // Esta lógica pode ser bastante complexa. Exemplo SIMPLIFICADO:

            PontoParagemDTO paragemDTO = PontoParagemDTO.builder()
                    .nome(paragem.getNome()).lat(paragem.getLat()).lon(paragem.getLon()).build();

            ViagemInfoDTO viagemDTO = ViagemInfoDTO.builder()
                    .veiculoId(1L).matriculaVeiculo("AA-01-BB").latAtual(-25.96).lonAtual(32.58).build();

            return AlunoViagemDTO.builder()
                    .nomeAluno(aluno.getNome())
                    .nomeRota("Rota da Manhã")
                    .pontoParagem(paragemDTO)
                    .viagem(viagemDTO)
                    .build();
        }).collect(Collectors.toList());


        return PainelEncarregadoDTO.builder()
                .nomeEncarregado(encarregado.getNome())
                .alunos(alunosViagem)
                .build();
    }
}