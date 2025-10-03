package com.gateway.serasa.app.controller;

import com.gateway.serasa.app.dto.PessoaResponseDTO;
import com.gateway.serasa.app.service.ConsultaSerasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {

    private final ConsultaSerasaService consultaService;

    public ConsultaController(ConsultaSerasaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping("/{documento}")
    public ResponseEntity<PessoaResponseDTO> consultarPorDocumento(@PathVariable String documento) {
        return ResponseEntity.ok(consultaService.consultarPorDocumento(documento));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<PessoaResponseDTO>> consultarPorLote(@RequestBody List<String> documentos) {
        return ResponseEntity.ok(consultaService.consultarPorLote(documentos));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<PessoaResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<PessoaResponseDTO> resultado = consultaService.buscarPorNome(nome);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/restricoes")
    public ResponseEntity<List<PessoaResponseDTO>> buscarPorRestricoes(@RequestParam String inicio, @RequestParam String fim) {
        List<PessoaResponseDTO> resultado = consultaService.buscarPorRestricoesEntreDatas(
                LocalDate.parse(inicio), LocalDate.parse(fim)
        );
        return ResponseEntity.ok(resultado);
    }
}
