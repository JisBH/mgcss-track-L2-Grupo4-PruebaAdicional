package com.mgcss.api.controller;

import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TecnicoController.class) 
class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TecnicoService tecnicoService;

    @Test
    void listar_DeberiaDevolverHttp200YListaDeTecnicos() throws Exception {
        when(tecnicoService.listarTecnicos()).thenReturn(List.of(
                new Tecnico(1L, true)
        ));

        mockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk()) // Código HTTP 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Estructura JSON correcta
                .andExpect(jsonPath("$[0].activo").value(true));
    }

    @Test
    void crear_DeberiaDevolverHttp201YElNuevoTecnico() throws Exception {
        when(tecnicoService.crearTecnico(true)).thenReturn(new Tecnico(2L, true));

        mockMvc.perform(post("/api/tecnicos"))
                .andExpect(status().isCreated()) // Código HTTP 201 Created
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.activo").value(true));
    }
}