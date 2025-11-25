package eda.projecto.kidtracker.service;


import eda.projecto.kidtracker.dto.EtaNotificationDTO;
import eda.projecto.kidtracker.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtaService {

    private final SimpMessagingTemplate messagingTemplate;

    // Fórmula de Haversine para calcular distância em linha reta (em km)
    private double calcularDistanciaHaversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Raio da Terra em km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    
    /**
     * Este é o método principal que é chamado quando uma nova localização é recebida.
     * @param viagemAtual A viagem que está a decorrer.
     * @param localizacaoAtual A última localização recebida do motorista.
     */
    public void processarLocalizacaoEEnviarNotificacoes(Viagem viagemAtual, Localizacao localizacaoAtual) {
        List<Rota> rotasDoVeiculo = viagemAtual.getVeiculo().getRotas();
    if (rotasDoVeiculo.isEmpty()) {
        return; // Veículo não tem rotas, não faz nada
    }
    Rota rota = rotasDoVeiculo.get(0); // Precisamos de um campo 'rotaAtiva' no Veiculo
        
        // Obter a lista ordenada de pontos de paragem para a rota atual
        List<PontoParagem> proximasParagens = obterProximasParagens(rota, localizacaoAtual);

        for (PontoParagem paragem : proximasParagens) {
            // Encontrar todos os alunos para esta paragem
            List<Aluno> alunosNaParagem = paragem.getAlunos();

            for (Aluno aluno : alunosNaParagem) {
                Familia familia = aluno.getFamilia();
                
                // 1. Calcular a distância em linha reta
                double distanciaKm = calcularDistanciaHaversine(
                    localizacaoAtual.getLat(), localizacaoAtual.getLon(),
                    paragem.getLat(), paragem.getLon()
                );
                
                // 2. CHAMADA À API EXTERNA PARA OBTER ETA (Exemplo conceptual)
                // String tempoEstimado = chamarGoogleMapsAPI(localizacaoAtual, paragem); // Ex: "12 min"

                String tempoEstimado = "15 min"; // Placeholder até integrares a API

                // 3. Montar e enviar a notificação para a família
                EtaNotificationDTO notificacao = EtaNotificationDTO.builder()
                    .alunoNome(aluno.getNome())
                    .pontoParagemNome(paragem.getNome())
                    .distanciaKm(String.format("%.1f km", distanciaKm))
                    .tempoEstimado(tempoEstimado)
                    .build();

                // Envia via WebSocket para um tópico específico da família
                String topico = "/topic/familia/" + familia.getId();
                messagingTemplate.convertAndSend(topico, notificacao);
            }
        }
    }
    
    // Métodos auxiliares (a serem implementados)
    private List<PontoParagem> obterProximasParagens(Rota rota, Localizacao localizacaoAtual) {
        // Lógica para determinar quais paragens ainda não foram visitadas
        return List.of(); // Placeholder
    }
}