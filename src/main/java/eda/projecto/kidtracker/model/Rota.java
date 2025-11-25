package eda.projecto.kidtracker.model;

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

    // Relação: Uma rota contém uma lista ordenada de paragens.
    // Usamos uma entidade de junção (RotaPontoParagem) para guardar a ordem (sequência).
    @OneToMany(
        mappedBy = "rota",
        cascade = CascadeType.ALL, // Se apagarmos uma rota, as suas associações de paragem também são apagadas.
        orphanRemoval = true
    )
    @OrderBy("sequencia ASC") // Garante que a lista de paragens vem sempre na ordem correta!
    private List<RotaPontoParagem> paragens;
}