package eda.projecto.kidtracker.repository;

import eda.projecto.kidtracker.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    /**
     * Procura as N atividades mais recentes, ordenadas por timestamp descendente.
     * O Spring Data JPA cria a query automaticamente a partir do nome do método.
     * @param count o número de atividades a retornar.
     * @return uma lista das atividades mais recentes.
     */
    List<Atividade> findTop5ByOrderByTimestampDesc(); // Exemplo para buscar as 5 mais recentes

}