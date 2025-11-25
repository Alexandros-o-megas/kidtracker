package eda.projecto.kidtracker.repository;

import eda.projecto.kidtracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Este método é crucial para encontrar um perfil pelo nome
    Optional<Role> findByNome(String nome);
}