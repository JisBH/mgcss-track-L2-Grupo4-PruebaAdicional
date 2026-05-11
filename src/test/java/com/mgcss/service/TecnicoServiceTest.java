package com.mgcss.service;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {

    @Mock
    private TecnicoRepository tecnicoRepoMock;

    @InjectMocks
    private TecnicoService tecnicoService;

    @Test
    void listarTecnicos_DeberiaDevolverLaListaDelRepositorio() {
        // Preparamos el mock para devolver 2 técnicos
        when(tecnicoRepoMock.findAll()).thenReturn(List.of(
                new Tecnico(1L, true),
                new Tecnico(2L, false)
        ));

        // Ejecución
        List<Tecnico> resultado = tecnicoService.listarTecnicos();

        // Comprobación
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
    }

    @Test
    void crearTecnico_DeberiaGuardarEnElRepositorio() {
        // Preparamos un técnico simulado que nos devolverá el repositorio al hacer "save"
        Tecnico tecnicoGuardado = new Tecnico(10L, true);
        when(tecnicoRepoMock.save(any(Tecnico.class))).thenReturn(tecnicoGuardado);

        // Ejecución
        Tecnico resultado = tecnicoService.crearTecnico(true);

        // Comprobación
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        verify(tecnicoRepoMock, times(1)).save(any(Tecnico.class)); // Verificamos que se llamó al repo
    }
}