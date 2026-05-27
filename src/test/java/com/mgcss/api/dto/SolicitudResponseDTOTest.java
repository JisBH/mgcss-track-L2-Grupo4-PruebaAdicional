package com.mgcss.api.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudResponseDTOTest {

    @Test
    void crearDTO_DeberiaAlmacenarYDevolverLosDatosCorrectamente() {
        LocalDate fechaEsperada = LocalDate.now();
        List<String> historialEsperado = List.of("ABIERTA", "EN_PROCESO");
        SolicitudResponseDTO.ClienteDTO clienteDTO = new SolicitudResponseDTO.ClienteDTO(10L, "Acme Corp");

        SolicitudResponseDTO dto = new SolicitudResponseDTO(
                1L, "Descripción test", "EN_PROCESO", fechaEsperada, 5L, clienteDTO, historialEsperado
        );

        assertEquals(1L, dto.id());
        assertEquals("Descripción test", dto.descripcion());
        assertEquals("EN_PROCESO", dto.estado());
        assertEquals(fechaEsperada, dto.fechaCreacion());
        assertEquals(5L, dto.tecnicoId());
        assertEquals(clienteDTO, dto.cliente());
        assertEquals(historialEsperado, dto.historial());
    }

    @Test
    void equals_DosDTOsConLosMismosDatos_DeberianSerIguales() {
        LocalDate fecha = LocalDate.now();
        SolicitudResponseDTO.ClienteDTO clienteDTO = new SolicitudResponseDTO.ClienteDTO(1L, "Test");
        
        SolicitudResponseDTO dto1 = new SolicitudResponseDTO(
                1L, "Desc", "ABIERTA", fecha, null, clienteDTO, List.of("ABIERTA")
        );
        SolicitudResponseDTO dto2 = new SolicitudResponseDTO(
                1L, "Desc", "ABIERTA", fecha, null, clienteDTO, List.of("ABIERTA")
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}