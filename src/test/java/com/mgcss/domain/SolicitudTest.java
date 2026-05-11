package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void crearSolicitud_DeberiaEstarAbiertaYTenerFecha() {
        // Ejecución
        Solicitud solicitud = new Solicitud();

        // Comprobación
        assertEquals(Solicitud.Estado.ABIERTA, solicitud.getEstado());
        assertNotNull(solicitud.getFechaCreacion());
        assertTrue(solicitud.getHistorialEstados().contains(Solicitud.Estado.ABIERTA));
    }

    @Test
    void iniciarProceso_DeberiaCambiarEstadoAEnProceso() {
        Solicitud solicitud = new Solicitud();
        
        solicitud.iniciarProceso();

        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
        assertEquals(2, solicitud.getHistorialEstados().size()); // ABIERTA -> EN_PROCESO
    }

    @Test
    void cerrarSolicitud_EstandoEnProceso_DeberiaFuncionar() {
        Solicitud solicitud = new Solicitud();
        solicitud.iniciarProceso(); // Primero debe estar en proceso
        
        solicitud.cerrar();

        assertEquals(Solicitud.Estado.CERRADA, solicitud.getEstado());
    }

    @Test
    void cerrarSolicitud_EstandoAbierta_DeberiaLanzarExcepcion() {
        Solicitud solicitud = new Solicitud(); // Está ABIERTA por defecto

        // Comprobamos que lance la regla de negocio
        assertThrows(ReglaNegocio.class, () -> {
            solicitud.cerrar();
        });
    }

    @Test
    void asignarTecnicoActivo_DeberiaFuncionar() {
        Solicitud solicitud = new Solicitud();
        Tecnico tecnicoActivo = new Tecnico(1L, true);

        solicitud.asignarTecnico(tecnicoActivo);

        assertEquals(tecnicoActivo, solicitud.getTecnico());
    }

    @Test
    void asignarTecnicoInactivo_DeberiaLanzarExcepcion() {
        Solicitud solicitud = new Solicitud();
        Tecnico tecnicoInactivo = new Tecnico(2L, false);

        assertThrows(ReglaNegocio.class, () -> {
            solicitud.asignarTecnico(tecnicoInactivo);
        });
    }
}