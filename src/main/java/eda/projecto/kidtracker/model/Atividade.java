package eda.projecto.kidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "atividade")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_atividade", nullable = false)
    private String tipo; // Ex: "TRIP_START", "NEW_DRIVER", "SPEED_ALERT"

    @Column(nullable = false, length = 512)
    private String texto; // Descrição legível da atividade

    @CreationTimestamp // Define automaticamente a data e hora de criação
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
    
    // Opcional: associar a atividade a um utilizador específico (quem a gerou)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    // Opcional: associar a atividade a um veículo específico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;
}