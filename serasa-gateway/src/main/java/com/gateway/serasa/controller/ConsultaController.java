package com.gateway.serasa.controller;

import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.service.ConsultaSerasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    private final ConsultaSerasaService consultaService;

    public ConsultaController(ConsultaSerasaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/{documento}")
    public ResponseEntity<?> consultarPorDocumento(@PathVariable String documento) {
        try {
            PessoaResponseDTO dto = consultaService.consultarPorDocumento(documento);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = Map.of("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("/lote")
    public ResponseEntity<?> consultarPorLote(@RequestBody List<String> documentos) {
        try {
            List<PessoaResponseDTO> listaDTO = consultaService.consultarPorLote(documentos);
            return ResponseEntity.ok(listaDTO);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = Map.of("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
}
