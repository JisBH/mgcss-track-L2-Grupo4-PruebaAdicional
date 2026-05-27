package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Solicitud;
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
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(1L);
        entity.setEstado(Solicitud.Estado.ABIERTA);
        entity.setDescripcion("Problema de red");
        ClienteEntity clienteEntity = new ClienteEntity("Cliente A");
        clienteEntity.setId(20L);
        entity.setCliente(clienteEntity);
        
        when(jpaRepo.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Solicitud> resultado = adapter.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Problema de red", resultado.get().getDescripcion());
        assertEquals(20L, resultado.get().getCliente().getId());
    }

    @Test
    void save_DeberiaMapearEntityYGuardar() {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        solicitud.setDescripcion("Revisión");
        com.mgcss.domain.Cliente cliente = new com.mgcss.domain.Cliente(15L, "Cliente B");
        solicitud.setCliente(cliente);
        
        SolicitudEntity entityRetorno = new SolicitudEntity();
        entityRetorno.setId(1L);
        
        when(jpaRepo.save(any(SolicitudEntity.class))).thenReturn(entityRetorno);

        Solicitud guardada = adapter.save(solicitud);

        assertNotNull(guardada);
        verify(jpaRepo).save(any(SolicitudEntity.class));
    }
    
    @Test
    void findById_SinClienteNiTecnico_DeberiaMapearCorrectamente() {
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(2L);
        entity.setEstado(Solicitud.Estado.ABIERTA);
        entity.setCliente(null); // Rama nula
        entity.setTecnico(null); // Rama nula

        when(jpaRepo.findById(2L)).thenReturn(Optional.of(entity));

        Optional<Solicitud> resultado = adapter.findById(2L);

        assertTrue(resultado.isPresent());
        assertNull(resultado.get().getCliente());
        assertNull(resultado.get().getTecnico());
    }

    @Test
    void save_ConIdCeroYSinCliente_DeberiaMapearEntity() {
        Solicitud solicitud = new Solicitud(); // ID será 0 por defecto
        solicitud.setCliente(null); // Forzar rama nula
        
        // Creamos la entidad que fingirá devolver la base de datos
        SolicitudEntity entityGuardada = new SolicitudEntity();
        entityGuardada.setId(10L); // <-- ¡ESTA ES LA CLAVE! Le damos un ID válido
        
        when(jpaRepo.save(any(SolicitudEntity.class))).thenReturn(entityGuardada);
        
        Solicitud resultado = adapter.save(solicitud);
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId()); // Comprobamos que el ID se mapea bien
    }
}