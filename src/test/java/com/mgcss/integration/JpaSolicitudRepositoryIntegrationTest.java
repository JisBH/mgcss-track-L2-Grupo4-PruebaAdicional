package com.mgcss.integration;


import com.mgcss.domain.MgcssTrackL2Grupo4Application;
import com.mgcss.domain.Solicitud;

import com.mgcss.infraestructure.persistence.JpaSolicitudRepository;
import com.mgcss.infraestructure.persistence.SolicitudEntity;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.junit.jupiter.api.Tag;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    
    
}