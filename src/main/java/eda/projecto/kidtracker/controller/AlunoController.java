package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.AlunoDTO; // Criaremos este DTO
import eda.projecto.kidtracker.service.AlunoService; // E este serviço
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/alunos")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Apenas Admins
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public List<AlunoDTO> getAllAlunos() {
        return alunoService.findAll();
    }

    // (Implementar POST, PUT, DELETE futuramente)
}