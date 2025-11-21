package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.model.Atividade;
import eda.projecto.kidtracker.model.Usuario;
import eda.projecto.kidtracker.model.Veiculo;
import eda.projecto.kidtracker.repository.AtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    
    /**
     * Método centralizado para registar uma nova atividade no sistema.
     * @param tipo O tipo de atividade (ex: "TRIP_START").
     * @param texto A descrição da atividade.
     * @param usuario Opcional, o utilizador que gerou a atividade.
     * @param veiculo Opcional, o veículo relacionado com a atividade.
     */
    @Transactional
    public void criarAtividade(String tipo, String texto, Usuario usuario, Veiculo veiculo) {
        Atividade novaAtividade = new Atividade();
        novaAtividade.setTipo(tipo);
        novaAtividade.setTexto(texto);
        novaAtividade.setUsuario(usuario);
        novaAtividade.setVeiculo(veiculo);
        
        atividadeRepository.save(novaAtividade);
    }
    
    /**
     * Obtém as 5 atividades mais recentes do sistema.
     * @return Uma lista de entidades Atividade.
     */
    @Transactional(readOnly = true)
    public List<Atividade> listarRecentes() {
        return atividadeRepository.findTop5ByOrderByTimestampDesc();
    }
}
