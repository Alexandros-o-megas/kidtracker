package eda.projecto.kidtracker.service;
import eda.projecto.kidtracker.dto.RotaDTO;
import eda.projecto.kidtracker.repository.RotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RotaService {
    private final RotaRepository rotaRepository;

    public List<RotaDTO> findAll() {
        return rotaRepository.findAll().stream()
                .map(RotaDTO::fromEntity)
                .collect(Collectors.toList());
    }
}