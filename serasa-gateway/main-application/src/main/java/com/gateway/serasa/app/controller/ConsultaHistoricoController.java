package com.gateway.serasa.app.controller;

import com.gateway.serasa.app.dto.ConsultaHistoricoPeriodoRequestDTO;
import com.gateway.serasa.app.dto.ConsultaHistoricoResponseDTO;
import com.gateway.serasa.app.dto.ConsultaHistoricoResumoResponseDTO;
import com.gateway.serasa.app.service.ConsultaHistoricoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
@RequiredArgsConstructor
public class ConsultaHistoricoController {

    private final ConsultaHistoricoService historicoService;

    @GetMapping("/consultas")
    public List<ConsultaHistoricoResponseDTO> listarTodasConsultas() {
        return historicoService.listarTodasConsultas();
    }

    @GetMapping("/consultas/resumo")
    public List<ConsultaHistoricoResumoResponseDTO> listarResumoTotalConsultas() {
        return historicoService.listarResumoTotalConsultas();
    }

    @PostMapping("/consultas/periodo")
    public List<ConsultaHistoricoResponseDTO> listarConsultasPorPeriodo(
            @RequestBody ConsultaHistoricoPeriodoRequestDTO request) {
        return historicoService.listarConsultasPorPeriodo(request);
    }

    @PostMapping("/consultas/resumo/periodo")
    public List<ConsultaHistoricoResumoResponseDTO> listarResumoPorPeriodo(
            @RequestBody ConsultaHistoricoPeriodoRequestDTO request) {
        return historicoService.listarResumoPorPeriodo(request);
    }
}