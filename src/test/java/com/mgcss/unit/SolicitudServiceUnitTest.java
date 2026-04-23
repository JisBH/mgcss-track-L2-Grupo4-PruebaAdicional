package com.mgcss.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import com.mgcss.service.SolicitudService;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceUnitTest {

    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private SolicitudService tecnicoService;

    @Test
    void testCargaContextoMockito() {
        // Verifica que los mocks se inyectan correctamente
        assertNotNull(tecnicoRepository);
        assertNotNull(tecnicoService);
    }
}