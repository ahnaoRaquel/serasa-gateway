package com.gateway.serasa.service;

import com.gateway.serasa.entity.ConsultaHistorico;
import com.gateway.serasa.entity.Pessoa;
import com.gateway.serasa.repository.ConsultaHistoricoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ConsultaHistoricoServiceTest {

    private ConsultaHistoricoRepository historicoRepository;
    private ConsultaHistoricoService historicoService;

    @BeforeEach
    void setUp() {
        historicoRepository = mock(ConsultaHistoricoRepository.class);
        historicoService = new ConsultaHistoricoService(historicoRepository);
    }

    @Test
    @DisplayName("Deve registrar uma nova consulta no histórico")
    void deveRegistrarConsultaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDocumento("02205379259");
        pessoa.setNome("João da Silva");

        ArgumentCaptor<ConsultaHistorico> captor = ArgumentCaptor.forClass(ConsultaHistorico.class);

        when(historicoRepository.save(any(ConsultaHistorico.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // retorna o mesmo objeto

        ConsultaHistorico resultado = historicoService.registrarConsulta(pessoa, "Consulta realizada com sucesso.");

        verify(historicoRepository, times(1)).save(captor.capture());
        ConsultaHistorico salvo = captor.getValue();

        assertNotNull(salvo);
        assertEquals("02205379259", salvo.getDocumentoConsultado());
        assertEquals("Consulta realizada com sucesso.", salvo.getResultadoResumo());
        assertNotNull(salvo.getDataConsulta());
        assertEquals(pessoa, salvo.getPessoa());

        assertNotNull(resultado);
        assertEquals("02205379259", resultado.getDocumentoConsultado());
    }

    @Test
    @DisplayName("Deve listar histórico por documento")
    void deveListarHistoricoPorDocumento() {
        String documento = "02205379259";
        ConsultaHistorico h1 = new ConsultaHistorico();
        h1.setDocumentoConsultado(documento);
        h1.setDataConsulta(LocalDateTime.now());

        ConsultaHistorico h2 = new ConsultaHistorico();
        h2.setDocumentoConsultado(documento);
        h2.setDataConsulta(LocalDateTime.now().minusDays(1));

        when(historicoRepository.findByDocumentoConsultado(documento))
                .thenReturn(Arrays.asList(h1, h2));

        List<ConsultaHistorico> lista = historicoService.listarPorDocumento(documento);

        verify(historicoRepository, times(1)).findByDocumentoConsultado(documento);
        assertEquals(2, lista.size());
        assertTrue(lista.stream().allMatch(h -> h.getDocumentoConsultado().equals(documento)));
    }

    @Test
    @DisplayName("Deve listar histórico por intervalo de datas")
    void deveListarHistoricoPorPeriodo() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(10);
        LocalDateTime fim = LocalDateTime.now();

        ConsultaHistorico h1 = new ConsultaHistorico();
        h1.setDocumentoConsultado("123");
        h1.setDataConsulta(LocalDateTime.now().minusDays(5));

        ConsultaHistorico h2 = new ConsultaHistorico();
        h2.setDocumentoConsultado("456");
        h2.setDataConsulta(LocalDateTime.now().minusDays(2));

        when(historicoRepository.findByDataConsultaBetween(inicio, fim))
                .thenReturn(Arrays.asList(h1, h2));

        List<ConsultaHistorico> lista = historicoService.listarPorPeriodo(inicio, fim);

        verify(historicoRepository, times(1)).findByDataConsultaBetween(inicio, fim);
        assertEquals(2, lista.size());
        assertTrue(lista.stream().allMatch(h -> !h.getDataConsulta().isBefore(inicio)
                && !h.getDataConsulta().isAfter(fim)));
    }
}