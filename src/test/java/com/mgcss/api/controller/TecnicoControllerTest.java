package com.mgcss.api.controller;

import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TecnicoController.class) // Solo carga este controlador
class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simulador de peticiones HTTP

    @Mock
    private TecnicoService tecnicoServiceMock; // Simulamos el servicio

    @Test
    void listar_DeberiaDevolverHttp200YListaDeTecnicos() throws Exception {
        // 1. Preparamos el mock
        when(tecnicoServiceMock.listarTecnicos()).thenReturn(List.of(
                new Tecnico(1L, true)
        ));

        // 2. Ejecutamos la petición GET y verificamos (Expect) el resultado
        mockMvc.perform(get("/api/tecnicos"))
                .andExpect(status().isOk()) // Esperamos HTTP 200
                .andExpect(jsonPath("$[0].id").value(1)) // Comprobamos el JSON devuelto
                .andExpect(jsonPath("$[0].activo").value(true));
    }

    @Test
    void crear_DeberiaDevolverHttp201YElNuevoTecnico() throws Exception {
        // 1. Preparamos el mock
        when(tecnicoServiceMock.crearTecnico(true)).thenReturn(new Tecnico(2L, true));

        // 2. Ejecutamos la petición POST y verificamos
        mockMvc.perform(post("/api/tecnicos"))
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.activo").value(true));
    }
}