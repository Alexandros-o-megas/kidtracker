package eda.projecto.kidtracker.dto;

import lombok.Data;

@Data
public class LocalizacaoDTO {
    private Double lat;
    private Double lon;
    private Double velocidade;
    private long timestamp; // Timestamp em milissegundos vindo do dispositivo
}
