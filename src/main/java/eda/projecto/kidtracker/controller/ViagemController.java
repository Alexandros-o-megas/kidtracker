package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.ViagemDTO; // Precisaremos de DTOs para viagem
import eda.projecto.kidtracker.service.ViagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService viagemService;

    @GetMapping("/motorista/proxima")
    //@PreAuthorize("hasRole('MOTORISTA')")
    public ResponseEntity<ViagemDTO> getProximaViagemMotorista() {
        ViagemDTO viagem = viagemService.findProximaViagemMotorista();
        return ResponseEntity.ok(viagem);
    }

    @PostMapping("/{id}/iniciar")
    @PreAuthorize("hasRole('MOTORISTA')")
    public ResponseEntity<?> iniciarViagem(@PathVariable Long id) {
        viagemService.iniciarViagem(id);
        return ResponseEntity.ok("Viagem iniciada com sucesso!");
    }

    @PostMapping("/paragens/{pontoParagemId}/cheguei")
    @PreAuthorize("hasRole('MOTORISTA')")
    public ResponseEntity<?> notificarChegadaParagem(@PathVariable Long pontoParagemId) {
        viagemService.processarChegadaParagem(pontoParagemId);
        return ResponseEntity.ok("Notificações de chegada enviadas.");
    }
}