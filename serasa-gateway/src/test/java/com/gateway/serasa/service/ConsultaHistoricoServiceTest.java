package com.gateway.serasa.service;

import com.gateway.serasa.dto.ConsultaHistoricoPeriodoRequestDTO;
import com.gateway.serasa.dto.ConsultaHistoricoResponseDTO;
import com.gateway.serasa.dto.ConsultaHistoricoResumoResponseDTO;
import com.gateway.serasa.entity.ConsultaHistorico;
import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.repository.ConsultaHistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaHistoricoServiceTest {

    @Mock
    private ConsultaHistoricoRepository historicoRepository;

    private ConsultaHistoricoService historicoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        historicoService = new ConsultaHistoricoService(historicoRepository);
    }

    @Test
    @DisplayName("Deve listar resumo total de consultas")
    void deveListarResumoTotalConsultas() {
        ConsultaHistoricoResumoResponseDTO resumo1 = new ConsultaHistoricoResumoResponseDTO("02205379259", "João", 5L);
        ConsultaHistoricoResumoResponseDTO resumo2 = new ConsultaHistoricoResumoResponseDTO("98765432100", "Maria", 3L);
        when(historicoRepository.findResumoTotalConsultas()).thenReturn(List.of(resumo1, resumo2));

        List<ConsultaHistoricoResumoResponseDTO> resultados = historicoService.listarResumoTotalConsultas();

        assertEquals(2, resultados.size());
        assertEquals("João", resultados.get(0).getNomePessoa());
        verify(historicoRepository, times(1)).findResumoTotalConsultas();
    }

    @Test
    @DisplayName("Deve listar resumo de consultas por período")
    void deveListarResumoPorPeriodo() {
        ConsultaHistoricoPeriodoRequestDTO request = new ConsultaHistoricoPeriodoRequestDTO(
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalDateTime.of(2025, 12, 31, 23, 59)
        );
        ConsultaHistoricoResumoResponseDTO resumo = new ConsultaHistoricoResumoResponseDTO("02205379259", "João", 2L);
        when(historicoRepository.findResumoConsultasPorPeriodo(request.getInicio(), request.getFim()))
                .thenReturn(List.of(resumo));

        List<ConsultaHistoricoResumoResponseDTO> resultados = historicoService.listarResumoPorPeriodo(request);

        assertEquals(1, resultados.size());
        verify(historicoRepository, times(1))
                .findResumoConsultasPorPeriodo(request.getInicio(), request.getFim());
    }

    @Test
    @DisplayName("Deve lançar exceção ao passar período inválido")
    void deveLancarExcecaoPeriodoInvalido() {
        ConsultaHistoricoPeriodoRequestDTO request = new ConsultaHistoricoPeriodoRequestDTO(
                LocalDateTime.of(2025, 12, 31, 0, 0),
                LocalDateTime.of(2025, 1, 1, 0, 0)
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> historicoService.listarResumoPorPeriodo(request));
        assertEquals("A data final não pode ser anterior à inicial.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar todas as consultas")
    void deveListarTodasConsultas() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setDocumento("02205379259");
        pessoa1.setNome("João");

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setDocumento("02205379259");
        pessoa2.setNome("Maria");

        ConsultaHistorico h1 = new ConsultaHistorico();
        h1.setPessoa(pessoa1);
        h1.setDataConsulta(LocalDateTime.of(2025, 6, 1, 12, 0));

        ConsultaHistorico h2 = new ConsultaHistorico();
        h2.setPessoa(pessoa2);
        h2.setDataConsulta(LocalDateTime.of(2025, 6, 2, 15, 0));

        when(historicoRepository.findAll()).thenReturn(List.of(h1, h2));

        List<ConsultaHistoricoResponseDTO> resultados = historicoService.listarTodasConsultas();

        assertEquals(2, resultados.size());
        assertEquals("02205379259", resultados.get(0).getDocumento());
        assertEquals("Maria", resultados.get(1).getNomePessoa());
    }

    @Test
    @DisplayName("Deve listar consultas por período")
    void deveListarConsultasPorPeriodo() {

        ConsultaHistoricoPeriodoRequestDTO request = new ConsultaHistoricoPeriodoRequestDTO(
                LocalDateTime.of(2025, 1, 1, 0, 0),
                LocalDateTime.of(2025, 12, 31, 23, 59)
        );

        Pessoa pessoa = new Pessoa();
        pessoa.setDocumento("02205379259");
        pessoa.setNome("João");

        ConsultaHistorico h = new ConsultaHistorico();
        h.setPessoa(pessoa);
        h.setDataConsulta(LocalDateTime.of(2025, 6, 1, 12, 0));

        when(historicoRepository.findByDataConsultaBetween(request.getInicio(), request.getFim()))
                .thenReturn(List.of(h));

        List<ConsultaHistoricoResponseDTO> resultados = historicoService.listarConsultasPorPeriodo(request);

        assertEquals(1, resultados.size());
        assertEquals("02205379259", resultados.get(0).getDocumento());
        assertEquals("João", resultados.get(0).getNomePessoa());
        verify(historicoRepository, times(1))
                .findByDataConsultaBetween(request.getInicio(), request.getFim());
    }
}