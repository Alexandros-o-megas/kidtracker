package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.LocalizacaoDTO;
import eda.projecto.kidtracker.model.Localizacao;
import eda.projecto.kidtracker.model.Veiculo;
import eda.projecto.kidtracker.repository.LocalizacaoRepository;
import eda.projecto.kidtracker.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // Injeção de dependências via construtor (Lombok)
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final SimpMessagingTemplate messagingTemplate; // Para enviar mensagens WebSocket

    @Transactional
    public void salvarETransmitirLocalizacao(Long veiculoId, LocalizacaoDTO dto) {
        // Passo 1: Valida se o veículo existe
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com o ID: " + veiculoId));

        // TODO: Encontrar a VIAGEM ativa atual para este veículo.
        // Por agora, vamos deixar a viagem como null para simplificar.

        // Passo 2: Criar e persistir a entidade Localizacao
        Localizacao novaLocalizacao = new Localizacao();
        novaLocalizacao.setLat(dto.getLat());
        novaLocalizacao.setLon(dto.getLon());
        novaLocalizacao.setVelocidade(dto.getVelocidade());
        // novaLocalizacao.setViagem(viagemAtiva); // Adicionar quando a lógica de viagem estiver pronta
        
        localizacaoRepository.save(novaLocalizacao);

        // Passo 3: Transmitir a localização via WebSocket para os subscritores
        // O tópico é dinâmico, baseado no ID do veículo
        String topico = "/topic/veiculo/" + veiculoId;
        messagingTemplate.convertAndSend(topico, dto);
        
        System.out.println("Localização recebida e enviada para o tópico: " + topico);
    }
}