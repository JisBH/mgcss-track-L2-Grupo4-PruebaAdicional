package com.mgcss.integration;

import com.mgcss.domain.Solicitud;
import com.mgcss.infraestructure.persistence.JpaSolicitudRepository;
import com.mgcss.infraestructure.persistence.SolicitudEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
class JpaSolicitudRepositoryIntegrationTest {

    @Autowired
    private JpaSolicitudRepository jpaRepository;

    @Test
    void guardarYRecuperarSolicitudEntity_DeberiaFuncionar() {
        // 1. Arrange: Preparamos la entidad técnica
        SolicitudEntity entity = new SolicitudEntity();
        entity.setEstado(Solicitud.Estado.EN_PROCESO);
        entity.setFechaCreacion(LocalDate.now());
        entity.setHistorialEstados(List.of(Solicitud.Estado.ABIERTA, Solicitud.Estado.EN_PROCESO));

        // 2. Act: Guardamos en la base de datos H2
        SolicitudEntity guardada = jpaRepository.save(entity);
        
        // Buscamos la entidad recién guardada
        SolicitudEntity recuperada = jpaRepository.findById(guardada.getId()).orElse(null);

        // 3. Assert: Comprobamos que JPA hizo su trabajo
        assertNotNull(recuperada);
        assertNotNull(recuperada.getId()); // H2 debió generar un ID automático
        assertEquals(Solicitud.Estado.EN_PROCESO, recuperada.getEstado());
        assertEquals(2, recuperada.getHistorialEstados().size());
    }
}