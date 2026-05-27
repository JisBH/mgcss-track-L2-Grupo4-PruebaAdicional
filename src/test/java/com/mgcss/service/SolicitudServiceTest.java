package com.mgcss.service;

import com.mgcss.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita el uso de Mocks
class SolicitudServiceTest {

    @Mock
    private SolicitudRepository solicitudRepoMock; // Repositorio de mentira

    @Mock
    private TecnicoRepository tecnicoRepoMock; // Repositorio de mentira

    @InjectMocks
    private SolicitudService solicitudService; // El servicio real, que usará los repos de mentira

    private Solicitud solicitudPrueba;
    
    @Mock
    private ClienteRepository clienteRepoMock;

    @BeforeEach
    void setUp() {
        // Preparamos unos datos básicos antes de cada test
        solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(100L);
    }

    @Test
    void consultarSolicitud_SiExiste_DeberiaDevolverla() {
        // Le decimos al Mock qué hacer cuando le pidan el ID 100
        when(solicitudRepoMock.findById(100L)).thenReturn(Optional.of(solicitudPrueba));

        Solicitud encontrada = solicitudService.consultarSolicitud(100L);

        assertEquals(100L, encontrada.getId());
    }

    @Test
    void consultarSolicitud_SiNoExiste_DeberiaLanzarEntidadNoEncontrada() {
        // Le decimos al Mock que devuelva vacío para el ID 999
        when(solicitudRepoMock.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntidadNoEncontrada.class, () -> {
            solicitudService.consultarSolicitud(999L);
        });
    }

    @Test
    void crearSolicitud_DeberiaGuardarEnRepositorio() {
        // 1. Simulamos que el cliente con ID 1 existe
        Cliente clienteSimulado = new Cliente(1L, "Cliente Test");
        when(clienteRepoMock.findById(1L)).thenReturn(Optional.of(clienteSimulado));
        
        // 2. Simulamos el guardado de la solicitud
        when(solicitudRepoMock.save(any(Solicitud.class))).thenReturn(solicitudPrueba);

        // 3. Ejecutamos el método con los NUEVOS parámetros
        Solicitud creada = solicitudService.crearSolicitud("Avería de prueba", 1L);

        assertNotNull(creada);
        // Verificamos que el repositorio guardó algo al menos 1 vez
        verify(solicitudRepoMock, times(1)).save(any(Solicitud.class)); 
    }

    @Test
    void asignarTecnico_DeberiaGuardarSolicitudModificada() {
        Tecnico tecnico = new Tecnico(50L, true);
        
        // Simulamos que la solicitud y el técnico existen
        when(solicitudRepoMock.findById(100L)).thenReturn(Optional.of(solicitudPrueba));
        when(tecnicoRepoMock.findById(50L)).thenReturn(Optional.of(tecnico));

        solicitudService.asignarTecnico(100L, 50L);

        // Verificamos que el técnico se asignó
        assertEquals(50L, solicitudPrueba.getTecnico().getId());
        // Verificamos que se llamó al método save para guardar el cambio
        verify(solicitudRepoMock, times(1)).save(solicitudPrueba);
    }
    
    @Test
    void listarSolicitudes_DeberiaLlamarAlRepo() {
        when(solicitudRepoMock.findAll()).thenReturn(java.util.List.of(solicitudPrueba));
        java.util.List<Solicitud> lista = solicitudService.listarSolicitudes();
        assertFalse(lista.isEmpty());
    }

    @Test
    void cerrarSolicitud_DeberiaFuncionar() {
        solicitudPrueba.iniciarProceso(); // Para que pueda cerrarse
        when(solicitudRepoMock.findById(1L)).thenReturn(java.util.Optional.of(solicitudPrueba));
        
        solicitudService.cerrarSolicitud(1L);
        
        assertEquals(Solicitud.Estado.CERRADA, solicitudPrueba.getEstado());
        verify(solicitudRepoMock).save(solicitudPrueba);
    }
}