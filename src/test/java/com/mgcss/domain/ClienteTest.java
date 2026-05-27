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
    
    @Test
    void crearCliente_ConNombreNulo_DeberiaLanzarReglaNegocio() {
        assertThrows(ReglaNegocio.class, () -> new Cliente(null));
    }

    @Test
    void vincularSolicitud_NulaODuplicada_DeberiaManejarCorrectamente() {
        Cliente cliente = new Cliente("Test");
        
        // 1. Probar solicitud nula
        assertThrows(ReglaNegocio.class, () -> cliente.vincularSolicitud(null));
        
        // 2. Probar solicitud duplicada (debe ignorarla para cubrir el if !contains)
        Solicitud s = new Solicitud();
        cliente.vincularSolicitud(s);
        cliente.vincularSolicitud(s); // Se añade por segunda vez
        
        assertEquals(1, cliente.getSolicitudes().size());
    }
    
    @Test
    void crearCliente_ConNombreVacioOPurosEspacios_DeberiaLanzarReglaNegocio() {
        assertThrows(ReglaNegocio.class, () -> new Cliente("   "));
        assertThrows(ReglaNegocio.class, () -> new Cliente(""));
    }
    
    @Test
    void setters_DeberianModificarLosAtributos() {
        Cliente cliente = new Cliente(1L, "Inicial");
        
        // Ejecutamos las 2 líneas que faltan por cubrir
        cliente.setId(99L);
        cliente.setNombre("Modificado");
        
        // Comprobamos que funcionaron
        assertEquals(99L, cliente.getId());
        assertEquals("Modificado", cliente.getNombre());
    }
}