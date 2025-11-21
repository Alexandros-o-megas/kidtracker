package eda.projecto.kidtracker.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class RotaPontoParagemId implements Serializable {
    private Long rotaId;
    private Long pontoParagemId;
}