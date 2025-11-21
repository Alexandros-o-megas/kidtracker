package eda.projecto.kidtracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "veiculo")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String matricula;

    private String modelo;

    @ManyToOne
    @JoinColumn(name = "id_motorista", referencedColumnName = "id")
    private Usuario motorista;

    @OneToMany(mappedBy = "veiculo", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rota> rotas;
}
