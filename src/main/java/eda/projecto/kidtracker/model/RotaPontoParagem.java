package eda.projecto.kidtracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rota_pontos_paragem")
public class RotaPontoParagem {

    @EmbeddedId
    private RotaPontoParagemId id;

    // Relação com Rota
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rotaId") // Mapeia o 'rotaId' da nossa chave composta para esta relação
    @JoinColumn(name = "rota_id")
    @JsonBackReference
    private Rota rota;

    // Relação com PontoParagem
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pontoParagemId") // Mapeia o 'pontoParagemId' da chave composta
    @JoinColumn(name = "ponto_paragem_id")
    private PontoParagem pontoParagem;
    
    // A coluna extra que justifica a existência desta entidade
    @Column(nullable = false)
    private Integer sequencia; 
}