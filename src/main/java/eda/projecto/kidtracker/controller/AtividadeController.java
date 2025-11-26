package eda.projecto.kidtracker.controller;


import eda.projecto.kidtracker.dto.AtividadeDTO;
import eda.projecto.kidtracker.model.Atividade;
import eda.projecto.kidtracker.service.AtividadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class AtividadeController {
    
    private final AtividadeService atividadeService;
    
    @GetMapping("/recent")
    public List<AtividadeDTO> getRecentActivities() {
        return atividadeService.listarRecentes();
    }
}
