package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.VeiculoDTO;
import eda.projecto.kidtracker.dto.VeiculoRequest;
import eda.projecto.kidtracker.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @GetMapping
    public List<VeiculoDTO> getAllVeiculos() { // MUDANÇA: Deve retornar List<VeiculoDTO>
        return veiculoService.findAll();
    }
    
    @PostMapping
    public VeiculoDTO createVeiculo(@RequestBody VeiculoRequest request) { // MUDANÇA: Recebe VeiculoRequest e retorna VeiculoDTO
        return veiculoService.create(request); // MUDANÇA: Chama o método create que criámos no serviço
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> updateVeiculo(@PathVariable Long id, @RequestBody VeiculoRequest request) { // MUDANÇA: Recebe VeiculoRequest
        VeiculoDTO updatedVeiculo = veiculoService.update(id, request);
        return ResponseEntity.ok(updatedVeiculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}