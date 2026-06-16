package com.lucassivolella.webchamados_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.lucassivolella.webchamados_backend.dto.ChamadoRequestDTO;
import com.lucassivolella.webchamados_backend.dto.ChamadoResponseDTO;
import com.lucassivolella.webchamados_backend.entity.Chamado;
import com.lucassivolella.webchamados_backend.entity.Responsavel;
import com.lucassivolella.webchamados_backend.enums.Prioridade;
import com.lucassivolella.webchamados_backend.enums.ChamadoStatus;
import com.lucassivolella.webchamados_backend.repository.ChamadoRepository;
import com.lucassivolella.webchamados_backend.service.ChamadoService;
import com.lucassivolella.webchamados_backend.service.ResponsavelService;

@ExtendWith(MockitoExtension.class)
public class ChamadoServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @Mock
    private ResponsavelService responsavelService;

    @InjectMocks
    private ChamadoService chamadoService;

    private Responsavel responsavel;
    private Chamado chamado;
    private ChamadoRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {

        responsavel = new Responsavel();
        responsavel.setId(1);
        responsavel.setNome("Lucas");
        responsavel.setEmail("lucas@email.com");

        chamado = new Chamado();
        chamado.setId(1);
        chamado.setTitulo("Erro sistema");
        chamado.setDescricao("Sistema não abre");
        chamado.setPrioridade(Prioridade.ALTA);
        chamado.setStatus(ChamadoStatus.ABERTO);
        chamado.setResponsavel(responsavel);
        chamado.setDataAbertura(LocalDateTime.now());
        chamado.setDataAtualizacao(LocalDateTime.now());

        requestDTO = new ChamadoRequestDTO(
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade().name(),
                chamado.getStatus().name(),
                chamado.getResponsavel().getId(),
                false);
    }

    @Test
    void deveCriarChamadoComSucesso() {

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenReturn(responsavel);

        when(chamadoRepository.save(any(Chamado.class)))
                .thenReturn(chamado);

        ChamadoResponseDTO response = chamadoService.criar(requestDTO);

        assertNotNull(response);
        assertEquals(chamado.getTitulo(), response.titulo());
        assertEquals(chamado.getPrioridade().name(), response.prioridade());
        assertEquals(chamado.getStatus().name(), response.status());

        verify(chamadoRepository).save(any(Chamado.class));
    }

    @Test
    void deveLancarExcecaoQuandoResponsavelNaoExisteAoCriar() {

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.criar(requestDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deveLancarExcecaoQuandoPrioridadeForInvalidaAoCriar() {

        ChamadoRequestDTO dtoInvalido = new ChamadoRequestDTO(
                "Titulo",
                "Descricao",
                "URGENTE",
                "ABERTO",
                responsavel.getId(),
                false);

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenReturn(responsavel);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.criar(dtoInvalido));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void deveAtualizarChamadoComSucesso() {

        ChamadoRequestDTO dtoAtualizacao = new ChamadoRequestDTO(
                "Novo titulo",
                "Nova descricao",
                "MEDIA",
                "EM_ANDAMENTO",
                responsavel.getId(),
                false);

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.of(chamado));

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenReturn(responsavel);

        when(chamadoRepository.save(any(Chamado.class)))
                .thenReturn(chamado);

        ChamadoResponseDTO response = chamadoService.atualizar(chamado.getId(), dtoAtualizacao);

        assertNotNull(response);
        assertEquals("Novo titulo", chamado.getTitulo());
        assertEquals("Nova descricao", chamado.getDescricao());
        assertEquals(Prioridade.MEDIA, chamado.getPrioridade());
        assertEquals(ChamadoStatus.EM_ANDAMENTO, chamado.getStatus());

        verify(chamadoRepository).save(any(Chamado.class));
    }

    @Test
    void deveLancarExcecaoQuandoChamadoNaoExisteAoAtualizar() {

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.atualizar(chamado.getId(), requestDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deveLancarExcecaoQuandoResponsavelNaoExisteAoAtualizar() {

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.of(chamado));

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.atualizar(chamado.getId(), requestDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deveLancarExcecaoQuandoPrioridadeForInvalidaAoAtualizar() {

        ChamadoRequestDTO dto = new ChamadoRequestDTO(
                "Titulo",
                "Descricao",
                "SUPER_ALTA",
                "ABERTO",
                responsavel.getId(),
                false);

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.of(chamado));

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenReturn(responsavel);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.atualizar(chamado.getId(), dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void deveLancarExcecaoQuandoStatusForInvalidoAoAtualizar() {

        ChamadoRequestDTO dto = new ChamadoRequestDTO(
                "Titulo",
                "Descricao",
                "ALTA",
                "FINALIZADO",
                responsavel.getId(),
                false);

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.of(chamado));

        when(responsavelService.buscarEntidadePorId(responsavel.getId()))
                .thenReturn(responsavel);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.atualizar(chamado.getId(), dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void deveBuscarChamadoPorId() {

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.of(chamado));

        ChamadoResponseDTO response = chamadoService.buscarPorId(chamado.getId());

        assertNotNull(response);
        assertEquals(chamado.getId(), response.id());
        assertEquals(chamado.getTitulo(), response.titulo());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarChamadoInexistente() {

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.buscarPorId(chamado.getId()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deveListarChamados() {

        Chamado chamado2 = new Chamado();
        chamado2.setId(2);
        chamado2.setTitulo("Segundo chamado");
        chamado2.setDescricao("Descricao");
        chamado2.setPrioridade(Prioridade.MEDIA);
        chamado2.setStatus(ChamadoStatus.EM_ANDAMENTO);
        chamado2.setResponsavel(responsavel);
        chamado2.setDataAbertura(LocalDateTime.now());
        chamado2.setDataAtualizacao(LocalDateTime.now());

        when(chamadoRepository.findAll())
                .thenReturn(Arrays.asList(chamado, chamado2));

        var lista = chamadoService.listar();

        assertEquals(2, lista.size());
    }

    @Test
    void deveFecharChamadoAoDeletar() {

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.of(chamado));

        chamadoService.deletar(chamado.getId());

        assertEquals(ChamadoStatus.FECHADO, chamado.getStatus());

        verify(chamadoRepository).save(chamado);
    }

    @Test
    void deveLancarExcecaoAoDeletarChamadoInexistente() {

        when(chamadoRepository.findById(chamado.getId()))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> chamadoService.deletar(chamado.getId()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}