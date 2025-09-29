package com.gateway.serasa.service;

import com.gateway.serasa.dto.ConsultaHistoricoPeriodoRequestDTO;
import com.gateway.serasa.dto.ConsultaHistoricoResponseDTO;
import com.gateway.serasa.dto.ConsultaHistoricoResumoResponseDTO;

import com.gateway.serasa.repository.ConsultaHistoricoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultaHistoricoService {

    private final ConsultaHistoricoRepository historicoRepository;

    public List<ConsultaHistoricoResumoResponseDTO> listarResumoTotalConsultas() {
        return historicoRepository.findResumoTotalConsultas();
    }

    public List<ConsultaHistoricoResumoResponseDTO> listarResumoPorPeriodo(ConsultaHistoricoPeriodoRequestDTO request) {
        validarPeriodo(request);
        return historicoRepository.findResumoConsultasPorPeriodo(request.getInicio(), request.getFim());
    }

    public List<ConsultaHistoricoResponseDTO> listarTodasConsultas() {
        return historicoRepository.findAll().stream()
                .map(h -> ConsultaHistoricoResponseDTO.builder()
                        .documento(h.getPessoa().getDocumento())
                        .nomePessoa(h.getPessoa().getNome())
                        .dataConsulta(h.getDataConsulta())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ConsultaHistoricoResponseDTO> listarConsultasPorPeriodo(ConsultaHistoricoPeriodoRequestDTO request) {
        validarPeriodo(request);
        return historicoRepository.findByDataConsultaBetween(request.getInicio(), request.getFim())
                .stream()
                .map(h -> ConsultaHistoricoResponseDTO.builder()
                        .documento(h.getPessoa().getDocumento())
                        .nomePessoa(h.getPessoa().getNome())
                        .dataConsulta(h.getDataConsulta())
                        .build())
                .collect(Collectors.toList());
    }

    private void validarPeriodo(ConsultaHistoricoPeriodoRequestDTO request) {
        if (request.getFim().isBefore(request.getInicio())) {
            throw new IllegalArgumentException("A data final não pode ser anterior à inicial.");
        }
    }
}