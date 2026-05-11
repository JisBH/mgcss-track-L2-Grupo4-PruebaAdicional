package com.mgcss.infraestructure.persistence;

import com.mgcss.domain.Tecnico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoRepositoryAdapterTest {

    @Mock
    private JpaTecnicoRepository jpaRepo;

    @InjectMocks
    private TecnicoRepositoryAdapter adapter;

    @Test
    void findAll_DeberiaMapearLista() {
        TecnicoEntity entity = new TecnicoEntity(true);
        entity.setId(1L);
        when(jpaRepo.findAll()).thenReturn(List.of(entity));

        List<Tecnico> resultado = adapter.findAll();

        assertFalse(resultado.isEmpty());
        assertEquals(1L, resultado.get(0).getId());
    }

    @Test
    void findById_CuandoNoExiste_DeberiaDevolverEmpty() {
        when(jpaRepo.findById(1L)).thenReturn(Optional.empty());
        Optional<Tecnico> resultado = adapter.findById(1L);
        assertTrue(resultado.isEmpty());
    }
}