package com.mgcss.domain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SolicitudTest {
	@Test
    void debeIniciarConEstadoAbierto() {
        Solicitud solicitud = new Solicitud();
        assertEquals(Solicitud.Estado.ABIERTA, solicitud.getEstado());
    }

    @Test
    void cerrarSolicitudEnProcesoFunciona() {
        Solicitud solicitud = new Solicitud();
        solicitud.iniciarProceso();
        solicitud.cerrar();
        assertEquals(Solicitud.Estado.CERRADA, solicitud.getEstado());
    }

    @Test
    void noSePuedeCerrarSiEstaAbierta() {
        Solicitud solicitud = new Solicitud();
        
        assertThrows(ReglaNegocio.class, solicitud::cerrar);
    }

    @Test
    void asignarTecnicoActivoFunciona() {
        Tecnico tecnico = new Tecnico(true);
        Solicitud solicitud = new Solicitud();
        solicitud.asignarTecnico(tecnico);
        assertEquals(tecnico, solicitud.getTecnico());
    }

    @Test
    void asignarTecnicoInactivoFalla() {
        Tecnico tecnico = new Tecnico(false);
        Solicitud solicitud = new Solicitud();
        
       
        assertThrows(ReglaNegocio.class, () -> {
            solicitud.asignarTecnico(tecnico);
        });
    }
    
    //sesion9
    @Test
    void reabrirSolicitudCerradaFunciona() {
        Solicitud solicitud = new Solicitud();
        solicitud.iniciarProceso();
        solicitud.cerrar();
        
        solicitud.reabrir(); // Este método aún no existe, dará error de compilación
        
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
    }
    
    @Test
    void debeRegistrarHistorialDeEstados() {
        Solicitud solicitud = new Solicitud(); // [ABIERTA]
        solicitud.iniciarProceso();            // [ABIERTA, EN_PROCESO]
        solicitud.cerrar();                    // [ABIERTA, EN_PROCESO, CERRADA]
        solicitud.reabrir();                   // [ABIERTA, EN_PROCESO, CERRADA, EN_PROCESO]

        java.util.List<Solicitud.Estado> historial = solicitud.getHistorialEstados();
        
        assertEquals(4, historial.size());
        assertEquals(Solicitud.Estado.ABIERTA, historial.get(0));
        assertEquals(Solicitud.Estado.CERRADA, historial.get(2));
        assertEquals(Solicitud.Estado.EN_PROCESO, historial.get(3));
    }
}
