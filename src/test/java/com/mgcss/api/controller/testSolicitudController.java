package com.mgcss.api.controller;

import com.mgcss.domain.MgcssTrackL2Grupo4Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.AsignarTecnicoRequestDTO;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolicitudController.class)
@ContextConfiguration(classes = MgcssTrackL2Grupo4Application.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    @MockitoBean
    private SolicitudService solicitudService;

    private Solicitud solicitudMock;

    @BeforeEach
    void setUp() {
       
        solicitudMock = new Solicitud();
        
    }

    @Test
    void debeCrearSolicitudYDevolver201() throws Exception {
        when(solicitudService.crearSolicitud()).thenReturn(solicitudMock);

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void debeConsultarSolicitudYDevolver200() throws Exception {
        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitudMock);

        mockMvc.perform(get("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void debeAsignarTecnicoYDevolver200() throws Exception {
        
        AsignarTecnicoRequestDTO requestDTO = new AsignarTecnicoRequestDTO(99L);
        
        mockMvc.perform(post("/api/solicitudes/1/tecnico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        
        verify(solicitudService).asignarTecnico(1L, 99L);
    }

    @Test
    void debeRechazarAsignacionSiTecnicoEsNuloYDevolver400() throws Exception {
      
        AsignarTecnicoRequestDTO requestDTO = new AsignarTecnicoRequestDTO(null);

        mockMvc.perform(post("/api/solicitudes/1/tecnico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest()); 
    }

    @Test
    void debeCambiarEstadoYDevolver204() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/estado")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(solicitudService).cambiarEstado(1L);
    }

    @Test
    void debeReabrirYDevolver204() throws Exception {
        mockMvc.perform(patch("/api/solicitudes/1/reabrir")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(solicitudService).reabrirSolicitud(1L);
    }
}