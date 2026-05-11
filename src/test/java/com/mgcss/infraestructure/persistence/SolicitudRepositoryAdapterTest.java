package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudRepositoryAdapterTest {

    @Mock
    private JpaSolicitudRepository jpaRepo;

    @Mock
    private JpaTecnicoRepository jpaTecnicoRepo;

    @InjectMocks
    private SolicitudRepositoryAdapter adapter;

    @Test
    void findById_CuandoExiste_DeberiaMapearADominio() {
        // Arrange
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(1L);
        entity.setEstado(Solicitud.Estado.ABIERTA);
        when(jpaRepo.findById(1L)).thenReturn(Optional.of(entity));

        // Act
        Optional<Solicitud> resultado = adapter.findById(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void save_DeberiaMapearEntityYGuardar() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(1L);
        
        when(jpaRepo.save(any(SolicitudEntity.class))).thenReturn(entity);

        // Act
        Solicitud guardada = adapter.save(solicitud);

        // Assert
        assertNotNull(guardada);
        verify(jpaRepo).save(any(SolicitudEntity.class));
    }
}