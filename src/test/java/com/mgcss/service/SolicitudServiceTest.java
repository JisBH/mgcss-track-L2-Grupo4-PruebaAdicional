package com.mgcss.service;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import com.mgcss.infraestructure.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudServiceTest {

	 @Test
	    void debeAsignarTecnicoCorrectamente() {

	        SolicitudRepository repoSolicitud = mock(SolicitudRepository.class);
	        TecnicoRepository repoTecnico = mock(TecnicoRepository.class);

	        SolicitudService service = new SolicitudService(repoSolicitud, repoTecnico);

	        Solicitud solicitud = new Solicitud();
	        Tecnico tecnicoActivo = new Tecnico(true);

	        when(repoSolicitud.findById(1L)).thenReturn(Optional.of(solicitud));
	        when(repoTecnico.findById(99L)).thenReturn(Optional.of(tecnicoActivo));

	        service.asignarTecnico(1L, 99L);

	        verify(repoSolicitud).save(solicitud);

	        assertEquals(tecnicoActivo, solicitud.getTecnico());
	    }
	 @Test
	    void debeLanzarExcepcionSiSolicitudNoExiste() {

	        SolicitudRepository repoSolicitud = mock(SolicitudRepository.class);
	        TecnicoRepository repoTecnico = mock(TecnicoRepository.class);

	        when(repoSolicitud.findById(1L)).thenReturn(Optional.empty());

	        SolicitudService service = new SolicitudService(repoSolicitud, repoTecnico);

	        assertThrows(RuntimeException.class, () -> {
	            service.asignarTecnico(1L, 99L);
	        });
	    }
}
