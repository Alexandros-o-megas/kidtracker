package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.model.*;
import eda.projecto.kidtracker.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViagemService {
    
    private final ViagemRepository viagemRepository;
    private final AtividadeService atividadeService; 
    
    @Transactional
    public Viagem iniciarViagem(Veiculo veiculo, Rota rota) {
        Viagem novaViagem = new Viagem();
        novaViagem.setVeiculo(veiculo);
        // novaViagem.setRota(rota); // Adicionar relação de Viagem para Rota se necessário
        viagemRepository.save(novaViagem);
        
        String textoAtividade = String.format("%s iniciou a \"%s\" com o veículo %s.",
                veiculo.getMotorista().getNome(),
                rota.getNome(),
                veiculo.getMatricula()
        );
        atividadeService.criarAtividade("TRIP_START", textoAtividade, veiculo.getMotorista(), veiculo);

        return novaViagem;
    }
}