package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.CreateMotoristaRequest;
import eda.projecto.kidtracker.dto.UsuarioDTO;
import eda.projecto.kidtracker.service.AuthService;
import eda.projecto.kidtracker.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importante
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')") // Garante que SÓ ADMINS acedem a este controller
public class AdminController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/motoristas")
    public ResponseEntity<?> createMotorista(@RequestBody CreateMotoristaRequest request) {
        try {
            authService.registerMotorista(request);
            return ResponseEntity.ok("Motorista criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/motoristas")
    public ResponseEntity<List<UsuarioDTO>> getAllMotoristas() {
        return ResponseEntity.ok(usuarioService.findAllMotoristas());
    }
}