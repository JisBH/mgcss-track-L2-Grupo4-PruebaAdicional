package com.mgcss.api.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudResponseDTOTest {

    @Test
    void crearDTO_DeberiaAlmacenarYDevolverLosDatosCorrectamente() {
        // 1. Arrange (Preparar): Definimos los datos de prueba
        long idEsperado = 1L;
        String estadoEsperado = "EN_PROCESO";
        LocalDate fechaEsperada = LocalDate.now();
        Long tecnicoIdEsperado = 5L;
        List<String> historialEsperado = List.of("ABIERTA", "EN_PROCESO");

        // 2. Act (Actuar): Instanciamos el record
        SolicitudResponseDTO dto = new SolicitudResponseDTO(
                idEsperado,
                estadoEsperado,
                estadoEsperado, fechaEsperada,
                tecnicoIdEsperado,
                null, historialEsperado
        );

        // 3. Assert (Comprobar): Verificamos que los datos se asignan y leen bien
        assertEquals(idEsperado, dto.id());
        assertEquals(estadoEsperado, dto.estado());
        assertEquals(fechaEsperada, dto.fechaCreacion());
        assertEquals(tecnicoIdEsperado, dto.tecnicoId());
        assertEquals(historialEsperado, dto.historial());
    }

    @Test
    void equals_DosDTOsConLosMismosDatos_DeberianSerIguales() {
        LocalDate fecha = LocalDate.now();
        
        SolicitudResponseDTO dto1 = new SolicitudResponseDTO(
                1L, "ABIERTA", null, fecha, null, null, List.of("ABIERTA")
        );
        
        SolicitudResponseDTO dto2 = new SolicitudResponseDTO(
                1L, "ABIERTA", null, fecha, null, null, List.of("ABIERTA")
        );

        // Comprobamos que ambos objetos se consideran exactamente iguales
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}