package eda.projecto.kidtracker.repository;
import eda.projecto.kidtracker.model.StatusViagem;
import eda.projecto.kidtracker.model.Usuario;
import eda.projecto.kidtracker.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    Optional<Viagem> findTopByVeiculo_MotoristaAndStatusOrderByInicioPrevistoAsc(Usuario motorista, StatusViagem status);
}