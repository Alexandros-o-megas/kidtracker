package eda.projecto.kidtracker.controller;

import eda.projecto.kidtracker.dto.LocalizacaoDTO;
import eda.projecto.kidtracker.service.LocalizacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/localizacoes")
@CrossOrigin(origins = "http://localhost:49202")
@RequiredArgsConstructor
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;

    @PostMapping("/veiculo/{veiculoId}")
    public ResponseEntity<Void> receberLocalizacao(@PathVariable Long veiculoId, @RequestBody LocalizacaoDTO localizacaoDTO) {
        // NOTA: Aqui, mais tarde, o Spring Security irá garantir que o motorista autenticado
        // só pode enviar a localização do seu próprio veículo.
        
        localizacaoService.salvarETransmitirLocalizacao(veiculoId, localizacaoDTO);
        return ResponseEntity.ok().build();
    }
}
