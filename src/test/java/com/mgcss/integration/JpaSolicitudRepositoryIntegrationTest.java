package com.mgcss.integration;


import com.mgcss.domain.MgcssTrackL2Grupo4Application;
import com.mgcss.domain.Solicitud;
import com.mgcss.infraestructure.persistence.JpaSolicitudRepository;
import com.mgcss.infraestructure.persistence.SolicitudEntity;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.Tag;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;


@ActiveProfiles("test")
@Tag("integration")
@DataJpaTest
@ContextConfiguration(classes = MgcssTrackL2Grupo4Application.class)
class JpaSolicitudRepositoryIntegrationTest {

    @Autowired
    private JpaSolicitudRepository repository;

    @Test
    void deberiaGuardarYRecuperarSolicitud() {
        SolicitudEntity solicitud = new SolicitudEntity();
        solicitud.setEstado(Solicitud.Estado.ABIERTA);
        solicitud.setFechaCreacion(LocalDate.now());

        SolicitudEntity saved = repository.save(solicitud);
        Optional<SolicitudEntity> result = repository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(Solicitud.Estado.ABIERTA, result.get().getEstado());
    }

    @Test
    void deberiaRetornarVacioSiIdNoExiste() {
        Optional<SolicitudEntity> result = repository.findById(999L);
        assertFalse(result.isPresent());
    }
    @Test
    void deberiaGuardarYRecuperarSolicitudConHistorial() {
        SolicitudEntity solicitud = new SolicitudEntity();
        solicitud.setEstado(Solicitud.Estado.EN_PROCESO);
        solicitud.setFechaCreacion(LocalDate.now());
        
        // Añadimos estados al historial
        solicitud.getHistorialEstados().add(Solicitud.Estado.ABIERTA);
        solicitud.getHistorialEstados().add(Solicitud.Estado.EN_PROCESO);

        SolicitudEntity saved = repository.save(solicitud);
        
        // Recuperamos de la BD para verificar persistencia real
        Optional<SolicitudEntity> result = repository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getHistorialEstados().size());
        assertEquals(Solicitud.Estado.ABIERTA, result.get().getHistorialEstados().get(0));
    }
    
}