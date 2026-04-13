package com.mgcss.domain;

import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudServiceTest {

    @Test
    void debeAsignarTecnicoCorrectamente() {
        // 1. Arrange (Preparar): Crear mocks y datos [cite: 333]
        SolicitudRepository repoSolicitud = mock(SolicitudRepository.class);
        TecnicoRepository repoTecnico = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(repoSolicitud, repoTecnico);

        Solicitud solicitud = new Solicitud(); // Estado ABIERTA por defecto
        Tecnico tecnicoActivo = new Tecnico(true); //Con false, falla.

        // Configurar el universo simulado
        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repoTecnico.findById(99L)).thenReturn(Optional.of(tecnicoActivo));

        // 2. Act (Ejecutar): Llamar al método del servicio
        service.asignarTecnico(1L, 99L);

        // 3. Assert & Verify: Verificar efectos secundarios
        // Comprobamos que se llamó al método save del repositorio
        verify(repoSolicitud).save(any(Solicitud.class));
        
        // Comprobamos que la lógica de negocio se aplicó
        assertNotNull(solicitud.getTecnico(), "La solicitud debería tener un técnico asignado");
        assertEquals(tecnicoActivo, solicitud.getTecnico());
    }
}
