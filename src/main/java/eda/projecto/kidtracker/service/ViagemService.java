package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.ChegadaNotificationDTO;
import eda.projecto.kidtracker.dto.ViagemDTO;
import eda.projecto.kidtracker.model.*;
import eda.projecto.kidtracker.repository.PontoParagemRepository;
import eda.projecto.kidtracker.repository.UsuarioRepository;
import eda.projecto.kidtracker.repository.ViagemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeService atividadeService;
    private final PontoParagemRepository pontoParagemRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional(readOnly = true)
    public ViagemDTO findProximaViagemMotorista() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario motorista = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com email: " + email));

        Viagem proximaViagem = viagemRepository
                .findTopByVeiculo_MotoristaAndStatusOrderByInicioPrevistoAsc(motorista, StatusViagem.AGENDADA)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma viagem agendada foi encontrada para o motorista " + motorista.getNome()));

        return ViagemDTO.fromEntity(proximaViagem);
    }

    @Transactional
    public void iniciarViagem(Long viagemId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario motoristaAutenticado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com email: " + email));

        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new EntityNotFoundException("Viagem não encontrada com o ID: " + viagemId));

        if (!viagem.getVeiculo().getMotorista().getId().equals(motoristaAutenticado.getId())) {
            throw new SecurityException("Acesso negado: Você não tem permissão para iniciar esta viagem.");
        }

        viagem.setStatus(StatusViagem.EM_CURSO);
        viagem.setInicioReal(LocalDateTime.now());
        viagemRepository.save(viagem);

        String textoAtividade = String.format("%s iniciou a viagem \"%s\" (veículo %s).",
                motoristaAutenticado.getNome(),
                viagem.getRota().getNome(),
                viagem.getVeiculo().getMatricula()
        );
        atividadeService.criarAtividade("TRIP_START", textoAtividade, motoristaAutenticado, viagem.getVeiculo());
    }

    @Transactional
    public void processarChegadaParagem(Long pontoParagemId) {
        // 1. Encontrar a paragem
        PontoParagem paragem = pontoParagemRepository.findById(pontoParagemId)
                .orElseThrow(() -> new EntityNotFoundException("Ponto de paragem não encontrado."));

        // 2. Encontrar todos os alunos nesta paragem
        List<Aluno> alunosNaParagem = paragem.getAlunos();

        // 3. Para cada aluno, notificar a sua família
        for (Aluno aluno : alunosNaParagem) {
            if (aluno.getFamilia() != null) {
                Long familiaId = aluno.getFamilia().getId();

                // 4. Construir a mensagem da notificação
                String mensagem = String.format("A carrinha chegou à paragem de %s (%s).",
                        aluno.getNome(), paragem.getNome());

                // O payload pode ser um simples objeto ou uma string
                // Vamos usar um DTO para consistência
                ChegadaNotificationDTO notificacao = new ChegadaNotificationDTO(mensagem, LocalDateTime.now());

                // 5. Enviar a notificação via WebSocket para o tópico específico da família
                String topico = "/topic/familia/" + familiaId;
                System.out.println("Enviando para o tópico: " + topico); // Para depuração
                messagingTemplate.convertAndSend(topico, notificacao);

            }
        }

    }
}