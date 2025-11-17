package eda.projecto.kidtracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data // Gera automaticamente getters, setters, toString, equals, hashCode
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    private String nome;

    @Column(nullable = false)
    private String role; // Ex: "ROLE_ADMIN", "ROLE_MOTORISTA", "ROLE_ENCARREGADO"
}
