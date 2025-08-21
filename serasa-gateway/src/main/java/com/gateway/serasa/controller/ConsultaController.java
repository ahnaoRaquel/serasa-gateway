package com.gateway.serasa.controller;

import com.gateway.serasa.dto.PessoaMapper;
import com.gateway.serasa.dto.PessoaResponseDTO;
import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.service.ConsultaSerasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            Pessoa pessoa = consultaService.consultarPorDocumento(documento);
            PessoaResponseDTO dto = PessoaMapper.toDTO(pessoa);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = Map.of("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PostMapping("/lote")
    public ResponseEntity<?> consultarPorLote(@RequestBody List<String> documentos) {
        try {
            List<PessoaResponseDTO> listaDTO = consultaService.consultarPorLote(documentos)
                    .stream()
                    .map(PessoaMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = Map.of("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
}
