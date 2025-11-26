package eda.projecto.kidtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChegadaNotificationDTO {
    private String message;
    private LocalDateTime timestamp;
}