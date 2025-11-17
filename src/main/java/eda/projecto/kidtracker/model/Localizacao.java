package eda.projecto.kidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "localizacao")
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat;
    private Double lon;
    private Double velocidade;

    @CreationTimestamp // Define automaticamente a hora de criação
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "id_viagem", referencedColumnName = "id")
    private Viagem viagem;
}