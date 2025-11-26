package eda.projecto.kidtracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "viagem")
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;
    
    private LocalDateTime fim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rota", nullable = false)
    private Rota rota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    @Column(name = "inicio_previsto", nullable = false)
    private LocalDateTime inicioPrevisto;

    @Column(name = "inicio_real")
    private LocalDateTime inicioReal;

    @Column(name = "fim_real")
    private LocalDateTime fimReal;

    // Um ENUM para o status da viagem é muito útil
    @Enumerated(EnumType.STRING)
    private StatusViagem status; // AGENDADA, EM_CURSO, CONCLUIDA, CANCELADA

    // Adicione a lista de localizações, se quiser
    @OneToMany(mappedBy = "viagem")
    private List<Localizacao> localizacoes;
}
