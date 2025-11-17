package eda.projecto.kidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;
    
    private LocalDateTime fim;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", referencedColumnName = "id")
    private Veiculo veiculo;
}
