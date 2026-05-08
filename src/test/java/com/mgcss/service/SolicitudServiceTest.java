package com.mgcss.service;

import com.mgcss.domain.EntidadNoEncontrada;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import com.mgcss.infraestructure.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository repoSolicitud;

    @Mock
    private TecnicoRepository repoTecnico;

    @InjectMocks
    private SolicitudService service;

    @Test
    void debeCrearSolicitud() {
        Solicitud nueva = new Solicitud();
        when(repoSolicitud.save(any(Solicitud.class))).thenReturn(nueva);

        Solicitud resultado = service.crearSolicitud();

        assertNotNull(resultado);
        verify(repoSolicitud).save(any(Solicitud.class));
    }

    @Test
    void debeConsultarSolicitudExistente() {
        Solicitud solicitud = new Solicitud();
        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));

        Solicitud resultado = service.consultarSolicitud(1L);

        assertNotNull(resultado);
        assertEquals(solicitud, resultado);
    }

    @Test
    void debeLanzarExcepcionAlConsultarSolicitudQueNoExiste() {
        when(repoSolicitud.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntidadNoEncontrada.class, () -> service.consultarSolicitud(1L));
    }

    @Test
    void debeListarSolicitudes() {
        List<Solicitud> listaMock = Arrays.asList(new Solicitud(), new Solicitud());
        when(repoSolicitud.findAll()).thenReturn(listaMock);

        List<Solicitud> resultado = service.listarSolicitudes();

        assertEquals(2, resultado.size());
        verify(repoSolicitud).findAll();
    }

    @Test
    void debeIniciarProcesoSolicitud() {
        Solicitud solicitud = new Solicitud();
        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));

        service.CambiarEstado(1L);

        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
        verify(repoSolicitud).save(solicitud);
    }


    @Test
    void debeReabrirSolicitud() {
        Solicitud solicitud = new Solicitud();
        solicitud.iniciarProceso();
        solicitud.cerrar(); 
        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));

        service.reabrirSolicitud(1L);

        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
        verify(repoSolicitud).save(solicitud);
    }

    @Test
    void debeAsignarTecnicoCorrectamente() {
        Solicitud solicitud = new Solicitud();
        Tecnico tecnicoActivo = new Tecnico(true);

        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repoTecnico.findById(99L)).thenReturn(Optional.of(tecnicoActivo));

        service.asignarTecnico(1L, 99L);

        verify(repoSolicitud).save(solicitud);
        assertEquals(tecnicoActivo, solicitud.getTecnico());
    }

    @Test
    void debeLanzarExcepcionSiTecnicoNoExisteAlAsignar() {
        Solicitud solicitud = new Solicitud();
        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repoTecnico.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntidadNoEncontrada.class, () -> service.asignarTecnico(1L, 99L));
    }
}