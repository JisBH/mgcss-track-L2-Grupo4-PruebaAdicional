package com.mgcss.api.controller;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Usamos solo Mockito, nada de Spring
class SolicitudControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SolicitudService solicitudServiceMock;

    @InjectMocks
    private SolicitudController solicitudController;

    private Solicitud solicitudPrueba;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(solicitudController).build();

        solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(1L);
        solicitudPrueba.setFechaCreacion(LocalDate.now());
        solicitudPrueba.setTecnico(new Tecnico(10L, true)); 
    }

    @Test
    void listarTodas_DeberiaDevolverHttp200YListaDeSolicitudes() throws Exception {
        when(solicitudServiceMock.listarSolicitudes()).thenReturn(List.of(solicitudPrueba));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("ABIERTA"))
                .andExpect(jsonPath("$[0].tecnicoId").value(10));
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void cambiarEstado_DeberiaDevolverHttp204() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/estado"))
                .andExpect(status().isNoContent());

        verify(solicitudServiceMock, times(1)).cambiarEstado(1L);
    }

    @Test
    void cerrar_DeberiaDevolverHttp204() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/cerrar"))
                .andExpect(status().isNoContent());

        verify(solicitudServiceMock, times(1)).cerrarSolicitud(1L);
    }

    @Test
    void asignarTecnico_DeberiaDevolverHttp200() throws Exception {
        String jsonPayload = "{\"tecnicoId\": 50}";

        mockMvc.perform(post("/api/solicitudes/1/tecnico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());

        verify(solicitudServiceMock, times(1)).asignarTecnico(1L, 50L);
    }
}