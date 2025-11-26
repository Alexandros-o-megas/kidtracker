package eda.projecto.kidtracker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    // Relação: Uma rota está associada a um veículo.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_veiculo")
    @ToString.Exclude
    private Veiculo veiculo;

    @OneToMany(
            mappedBy = "rota",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER // <<< ADICIONE/ALTERE ISTO
    )
    @OrderBy("sequencia ASC")
    @JsonManagedReference
    private List<RotaPontoParagem> paragens;
}