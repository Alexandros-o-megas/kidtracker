package eda.projecto.kidtracker.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class EtaNotificationDTO {
    private String alunoNome;
    private String pontoParagemNome;
    private String distanciaKm;
    private String tempoEstimado;
}