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
    
}
