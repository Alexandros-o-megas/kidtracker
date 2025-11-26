package eda.projecto.kidtracker.dto;
import lombok.Data;
@Data
public class CreateMotoristaRequest {
    private String nome;
    private String email;
    private String senha;
}