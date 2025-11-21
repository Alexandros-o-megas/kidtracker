package eda.projecto.kidtracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "aluno")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "id_familia", referencedColumnName = "id")
    private Familia familia;

    @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_ponto_paragem")
private PontoParagem pontoParagem;
}

