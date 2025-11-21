package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.model.Veiculo;
import eda.projecto.kidtracker.model.Viagem;
import eda.projecto.kidtracker.model.Rota;
import eda.projecto.kidtracker.repository.ViagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Exemplo de modificação no futuro ViagemService.java
@Service
@RequiredArgsConstructor
public class ViagemService {
    
    private final ViagemRepository viagemRepository;
    private final AtividadeService atividadeService; // 1. Injetar o novo serviço
    
    @Transactional
    public Viagem iniciarViagem(Veiculo veiculo, Rota rota) {
        // ... lógica para criar uma nova viagem ...
        
        Viagem novaViagem = new Viagem();
        // ... set veiculo, rota, inicio ...
        viagemRepository.save(novaViagem);
        
        // 2. Chamar o AtividadeService para registar o evento
        String textoAtividade = String.format("%s iniciou a \"%s\" com o veículo %s.",
                veiculo.getMotorista().getNome(),
                rota.getNome(),
                veiculo.getMatricula()
        );
        atividadeService.criarAtividade("TRIP_START", textoAtividade, veiculo.getMotorista(), veiculo);

        return novaViagem;
    }
}
