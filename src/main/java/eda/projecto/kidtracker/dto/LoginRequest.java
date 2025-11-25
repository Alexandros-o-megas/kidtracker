package eda.projecto.kidtracker.dto;
import lombok.Data;
@Data
public class LoginRequest {
    private String email;
    private String password; // Alterado de 'senha' para 'password' por consistência com Spring Security
}