package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias sobre la lógica pura y las invariantes del objeto
 * de dominio {@link Cliente}.
 */
class ClienteTest {

    @Test
    void crearClienteValido_DeberiaAsignarAtributos() {
        Cliente cliente = new Cliente("Acme Corp");
        assertEquals("Acme Corp", cliente.getNombre());
        assertEquals(0L, cliente.getId());
    }

    @Test
    void crearClienteConNombreVacio_DeberiaLanzarReglaNegocio() {
        assertThrows(ReglaNegocio.class, () -> new Cliente("   "));
    }

    @Test
    void vincularSolicitud_DeberiaAnadirALaLista() {
        Cliente cliente = new Cliente("Cliente Test");
        Solicitud solicitud = new Solicitud();
        
        cliente.vincularSolicitud(solicitud);
        
        assertEquals(1, cliente.getSolicitudes().size());
        assertTrue(cliente.getSolicitudes().contains(solicitud));
    }
}