package eda.projecto.kidtracker.repository;

import eda.projecto.kidtracker.model.PontoParagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoParagemRepository extends JpaRepository<PontoParagem, Long> {
    // No futuro, poderíamos adicionar métodos como:
    // List<PontoParagem> findByNomeContainingIgnoreCase(String nome);

}