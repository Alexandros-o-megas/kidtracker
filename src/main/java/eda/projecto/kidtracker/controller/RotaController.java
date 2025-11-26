package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.RotaDTO; // DTO que já criámos antes
import eda.projecto.kidtracker.service.RotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/rotas")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RotaController {

    private final RotaService rotaService;

    @GetMapping
    public List<RotaDTO> getAllRotas() {
        return rotaService.findAll();
    }
}