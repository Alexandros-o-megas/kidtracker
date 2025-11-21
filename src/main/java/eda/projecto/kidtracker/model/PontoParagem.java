package eda.projecto.kidtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "ponto_paragem")
public class PontoParagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;
    
    // Relação: Um ponto de paragem pode ter vários alunos.
    // 'mappedBy' indica que a entidade Aluno é a "dona" desta relação.
    @OneToMany(mappedBy = "pontoParagem", fetch = FetchType.LAZY)
    @JsonIgnore // Evita serialização em loop ao pedir um PontoParagem
    @ToString.Exclude // Evita recursão infinita no método toString() do Lombok
    private List<Aluno> alunos;
}