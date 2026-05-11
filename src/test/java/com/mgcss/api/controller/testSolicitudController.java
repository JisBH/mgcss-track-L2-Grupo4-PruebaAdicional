package com.mgcss.api.controller;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolicitudController.class) // Habilita solo el entorno web para este controlador
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc; // Herramienta de Spring para simular peticiones HTTP

    @Mock
    private SolicitudService solicitudServiceMock; // Simulamos el servicio de negocio

    private Solicitud solicitudPrueba;

    @BeforeEach
    void setUp() {
        // Preparamos una solicitud simulada que devolverá nuestro servicio Mock
        solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(1L);
        solicitudPrueba.setFechaCreacion(LocalDate.now());
        // Le asignamos un técnico para probar el mapeo del DTO
        solicitudPrueba.setTecnico(new Tecnico(10L, true)); 
    }

    @Test
    void listarTodas_DeberiaDevolverHttp200YListaDeSolicitudes() throws Exception {
        // 1. Arrange (Preparar): Le decimos al mock qué devolver
        when(solicitudServiceMock.listarSolicitudes()).thenReturn(List.of(solicitudPrueba));

        // 2. Act & Assert (Actuar y Comprobar)
        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk()) // Esperamos un 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // El JSON debe tener ID 1
                .andExpect(jsonPath("$[0].estado").value("ABIERTA")) // Estado por defecto
                .andExpect(jsonPath("$[0].tecnicoId").value(10)) // Verificamos que el DTO extrajo el ID del técnico
                .andExpect(jsonPath("$[0].comentarios[0]").value("Revisión inicial"));
    }

    @Test
    void consultar_SiExiste_DeberiaDevolverHttp200YLaSolicitud() throws Exception {
        when(solicitudServiceMock.consultarSolicitud(1L)).thenReturn(solicitudPrueba);

        mockMvc.perform(get("/api/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tecnicoId").value(10));
    }

    @Test
    void crear_DeberiaDevolverHttp201YLaNuevaSolicitud() throws Exception {
        when(solicitudServiceMock.crearSolicitud()).thenReturn(solicitudPrueba);

        mockMvc.perform(post("/api/solicitudes"))
                .andExpect(status().isCreated()) // Esperamos un 201 Created
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void cambiarEstado_DeberiaDevolverHttp204() throws Exception {
        // Como el método cambiarEstado es 'void', no necesitamos 'when(...).thenReturn(...)'
        // Solo verificamos que la petición responda correctamente
        mockMvc.perform(put("/api/solicitudes/1/estado"))
                .andExpect(status().isNoContent()); // Esperamos un 204 No Content

    }
}