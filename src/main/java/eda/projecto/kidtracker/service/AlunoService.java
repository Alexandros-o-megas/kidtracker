package eda.projecto.kidtracker.service;
import eda.projecto.kidtracker.dto.AlunoDTO;
import eda.projecto.kidtracker.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public List<AlunoDTO> findAll() {
        return alunoRepository.findAll().stream()
                .map(AlunoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}