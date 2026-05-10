package com.mgcss;

import com.mgcss.domain.TecnicoRepository;
import com.mgcss.infraestructure.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(classes = MgcssTrackL2Grupo4Application.class)
@ActiveProfiles("test") 
class MgcssTrackL2Grupo4ApplicationTests {

    @MockitoBean
    private SolicitudRepository solicitudRepository;

    @MockitoBean
    private TecnicoRepository tecnicoRepository;
}