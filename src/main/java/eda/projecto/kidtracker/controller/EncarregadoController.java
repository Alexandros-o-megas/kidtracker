package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.PainelEncarregadoDTO;
import eda.projecto.kidtracker.service.EncarregadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/encarregado")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ENCARREGADO')")
public class EncarregadoController {

    private final EncarregadoService encarregadoService;

    @GetMapping("/painel")
    public ResponseEntity<PainelEncarregadoDTO> getPainelData() {
        return ResponseEntity.ok(encarregadoService.getPainelInfo());
    }
}