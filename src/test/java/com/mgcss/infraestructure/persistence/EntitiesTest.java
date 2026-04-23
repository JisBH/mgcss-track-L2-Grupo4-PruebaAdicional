package com.mgcss.infraestructure.persistence;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import com.mgcss.domain.Solicitud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EntitiesTest {

    @Test
    void testTecnicoEntityGettersAndSetters() {
        TecnicoEntity tecnico = new TecnicoEntity();
        tecnico.setId(1L);
        tecnico.setActivo(true);
        
        assertEquals(1L, tecnico.getId());
        assertTrue(tecnico.isActivo());
        
        TecnicoEntity tecnico2 = new TecnicoEntity(false);
        assertFalse(tecnico2.isActivo());
    }

    @Test
    void testSolicitudEntityGettersAndSetters() {
        SolicitudEntity solicitud = new SolicitudEntity();
        TecnicoEntity tecnico = new TecnicoEntity(true);
        LocalDate fecha = LocalDate.now();
        
        solicitud.setId(10L);
        solicitud.setFechaCreacion(fecha);
        solicitud.setEstado(Solicitud.Estado.EN_PROCESO);
        solicitud.setTecnico(tecnico);
        
        assertEquals(10L, solicitud.getId());
        assertEquals(fecha, solicitud.getFechaCreacion());
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
        assertEquals(tecnico, solicitud.getTecnico());
    }
}