package com.mgcss.api.controller;

import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // Usamos solo Mockito, nada de Spring
class TecnicoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TecnicoService tecnicoServiceMock;

    @InjectMocks
    private TecnicoController tecnicoController;

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc en modo "standalone" (aislado)
        mockMvc = MockMvcBuilders.standaloneSetup(tecnicoController).build();
    }

    @Test
    void listar_DeberiaDevolverHttp200YListaDeTecnicos() throws Exception {
        when(tecnicoServiceMock.listarTecnicos()).thenReturn(List.of(
                new Tecnico(1L, true)
        ));

        mockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].activo").value(true));
    }

    @Test
    void crear_DeberiaDevolverHttp201YElNuevoTecnico() throws Exception {
        when(tecnicoServiceMock.crearTecnico(true)).thenReturn(new Tecnico(2L, true));

        mockMvc.perform(post("/api/tecnicos"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.activo").value(true));
    }
}