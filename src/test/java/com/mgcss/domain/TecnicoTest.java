package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TecnicoTest {

    @Test
    void crearNuevoTecnico_DeberiaAsignarEstadoCorrectamente() {
        // Ejecución: Creamos un técnico activo
        Tecnico tecnico = new Tecnico(true);

        // Comprobación
        assertTrue(tecnico.isActivo());
        assertEquals(0L, tecnico.getId()); // Como es nuevo, su ID debe ser 0
    }

    @Test
    void reconstruirTecnico_DesdeBaseDeDatos_DeberiaMantenerDatos() {
        // Ejecución: Simulamos que viene de la base de datos con ID 5
        Tecnico tecnico = new Tecnico(5L, false);

        // Comprobación
        assertEquals(5L, tecnico.getId());
        assertFalse(tecnico.isActivo());
    }

    @Test
    void cambiarEstado_DeberiaActualizarElValor() {
        Tecnico tecnico = new Tecnico(true);
        
        tecnico.setActivo(false);

        assertFalse(tecnico.isActivo());
    }
}