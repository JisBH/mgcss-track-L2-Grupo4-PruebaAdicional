package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryAdapterTest {

    @Mock
    private JpaClienteRepository jpaRepo;

    @InjectMocks
    private ClienteRepositoryAdapter adapter;

    @Test
    void save_DeberiaMapearYGuardar() {
        Cliente cliente = new Cliente(1L, "Test");
        ClienteEntity entity = new ClienteEntity("Test");
        entity.setId(1L);
        when(jpaRepo.save(any(ClienteEntity.class))).thenReturn(entity);
        
        Cliente guardado = adapter.save(cliente);
        assertEquals(1L, guardado.getId());
    }

    @Test
    void findById_MapeaCorrectamente() {
        ClienteEntity entity = new ClienteEntity("Test");
        entity.setId(1L);
        when(jpaRepo.findById(1L)).thenReturn(Optional.of(entity));
        assertTrue(adapter.findById(1L).isPresent());
    }

    @Test
    void findAll_MapeaCorrectamente() {
        when(jpaRepo.findAll()).thenReturn(List.of(new ClienteEntity("Test")));
        assertFalse(adapter.findAll().isEmpty());
    }
}