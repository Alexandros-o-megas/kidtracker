package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.AuthResponse; // Terás que criar este
import eda.projecto.kidtracker.dto.LoginRequest; // E este
import eda.projecto.kidtracker.dto.RegisterRequest;
import eda.projecto.kidtracker.model.Usuario;
import eda.projecto.kidtracker.service.AuthService; // E este serviço
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; // Serviço que conterá a lógica de login

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    // O teu AuthContext também faz uma chamada getProfile()
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Usuario userProfile = authService.getCurrentUserProfile();
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest);
            return ResponseEntity.ok("Utilizador registado com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
