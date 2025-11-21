package eda.projecto.kidtracker.service;

import eda.projecto.kidtracker.dto.VeiculoDTO;
import eda.projecto.kidtracker.dto.VeiculoRequest;
import eda.projecto.kidtracker.model.Usuario;
import eda.projecto.kidtracker.model.Veiculo;
import eda.projecto.kidtracker.repository.UsuarioRepository;
import eda.projecto.kidtracker.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contém a lógica de negócio para a gestão de veículos.
 */
@Service
@RequiredArgsConstructor // Injeta os repositórios via construtor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository; // Necessário para associar motoristas

    /**
     * Lista todos os veículos existentes no sistema.
     * @return Uma lista de VeiculoDTO.
     */
    @Transactional(readOnly = true) // Transação apenas de leitura é mais eficiente
    public List<VeiculoDTO> findAll() {
        return veiculoRepository.findAll().stream()
                .map(VeiculoDTO::fromEntity) // Converte cada Veiculo para VeiculoDTO
                .collect(Collectors.toList());
    }

    /**
     * Procura um veículo pelo seu ID.
     * @param id O ID do veículo.
     * @return Um VeiculoDTO correspondente.
     * @throws EntityNotFoundException se o veículo não for encontrado.
     */
    @Transactional(readOnly = true)
    public VeiculoDTO findById(Long id) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com o ID: " + id));
        return VeiculoDTO.fromEntity(veiculo);
    }
    
    /**
     * Cria um novo veículo no sistema.
     * @param request DTO com os dados do novo veículo.
     * @return O VeiculoDTO recém-criado.
     */
    @Transactional
    public VeiculoDTO create(VeiculoRequest request) {
        Veiculo novoVeiculo = new Veiculo();
        novoVeiculo.setMatricula(request.getMatricula());
        novoVeiculo.setModelo(request.getModelo());
        // Adicionar capacidade e status quando existirem na entidade Veiculo.

        // Se um motoristaId for fornecido, associa-o ao veículo
        if (request.getMotoristaId() != null) {
            Usuario motorista = usuarioRepository.findById(request.getMotoristaId())
                    .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com o ID: " + request.getMotoristaId()));
            novoVeiculo.setMotorista(motorista);
        }
        
        Veiculo veiculoSalvo = veiculoRepository.save(novoVeiculo);
        return VeiculoDTO.fromEntity(veiculoSalvo);
    }
    
    /**
     * Atualiza os dados de um veículo existente.
     * @param id O ID do veículo a ser atualizado.
     * @param request DTO com os novos dados do veículo.
     * @return O VeiculoDTO atualizado.
     */
    @Transactional
    public VeiculoDTO update(Long id, VeiculoRequest request) {
        // Encontra o veículo existente ou lança uma exceção
        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com o ID: " + id));

        // Atualiza os campos
        veiculoExistente.setMatricula(request.getMatricula());
        veiculoExistente.setModelo(request.getModelo());

        // Atualiza a associação do motorista
        if (request.getMotoristaId() != null) {
            Usuario motorista = usuarioRepository.findById(request.getMotoristaId())
                    .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com o ID: " + request.getMotoristaId()));
            veiculoExistente.setMotorista(motorista);
        } else {
            veiculoExistente.setMotorista(null); // Permite desassociar um motorista
        }

        Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExistente);
        return VeiculoDTO.fromEntity(veiculoAtualizado);
    }

    /**
     * Elimina um veículo do sistema.
     * @param id O ID do veículo a ser eliminado.
     */
    @Transactional
    public void deleteById(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new EntityNotFoundException("Veículo não encontrado com o ID: " + id);
        }
        veiculoRepository.deleteById(id);
    }

    
}
