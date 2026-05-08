package com.mgcss.api.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testSolicitudResponseDTO() {
        List<String> historial = List.of("ABIERTA", "EN_PROCESO");
        SolicitudResponseDTO dto = new SolicitudResponseDTO(1L, "EN_PROCESO", LocalDate.now(), 99L, historial);

        // Llamamos a los getters para asegurar que se cubren en el análisis
        assertEquals(1L, dto.id());
        assertEquals("EN_PROCESO", dto.estado());
        assertNotNull(dto.fechaCreacion());
        assertEquals(99L, dto.tecnicoId());
        assertEquals(2, dto.historial().size());
    }

    @Test
    void testAsignarTecnicoRequestDTO() {
        AsignarTecnicoRequestDTO dto = new AsignarTecnicoRequestDTO(99L);
        assertEquals(99L, dto.tecnicoId());
    }

    @Test
    void testSolicitudRequestDTO() {
        SolicitudRequestDTO dto = new SolicitudRequestDTO();
        assertNotNull(dto);
    }
}