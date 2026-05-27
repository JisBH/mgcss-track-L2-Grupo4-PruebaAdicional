package com.mgcss.api.controller;

import com.mgcss.domain.EntidadNoEncontrada;
import com.mgcss.domain.ReglaNegocio;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolicitudController.class) // Paso 3.1: No levanta contexto completo, solo controladores y filtros web
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Paso 3.1: Registra y mockea el servicio directamente en el contexto de Spring
    private SolicitudService solicitudService;

    // --- PASO 3.2: VERIFICAR CÓDIGOS HTTP Y ESTRUCTURA JSON CORRECTA ---

    @Test
    void listarTodas_DeberiaDevolverHttp200YListaDeSolicitudes() throws Exception {
        Solicitud solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(1L);
        solicitudPrueba.setTecnico(new Tecnico(10L, true));

        when(solicitudService.listarSolicitudes()).thenReturn(List.of(solicitudPrueba));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk()) // Código HTTP 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Estructura JSON correcta
                .andExpect(jsonPath("$[0].estado").value("ABIERTA"))
                .andExpect(jsonPath("$[0].tecnicoId").value(10));
    }

    @Test
    void consultar_SiExiste_DeberiaDevolverHttp200YLaSolicitud() throws Exception {
        Solicitud solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(1L);

        when(solicitudService.consultarSolicitud(1L)).thenReturn(solicitudPrueba);

        mockMvc.perform(get("/api/solicitudes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void crear_DeberiaDevolverHttp201YLaNuevaSolicitud() throws Exception {
        // 1. Preparamos la respuesta simulada
        Solicitud solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(1L);
        solicitudPrueba.setDescripcion("Nueva tarea");

        // Usamos anyString() y anyLong() porque la firma del servicio cambió
        when(solicitudService.crearSolicitud(anyString(), anyLong())).thenReturn(solicitudPrueba);

        // 2. Preparamos el JSON que simula enviar el frontend
        String jsonPayload = """
            {
                "descripcion": "Nueva tarea",
                "clienteId": 1
            }
            """;

        // 3. Hacemos la petición POST enviando el JSON
        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isCreated()) // Ahora sí devolverá 201 Created
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void cambiarEstado_DeberiaDevolverHttp204() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/estado"))
                .andExpect(status().isNoContent()); // Código HTTP 204 No Content

        verify(solicitudService, times(1)).cambiarEstado(1L);
    }

    @Test
    void cerrar_DeberiaDevolverHttp204() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/cerrar"))
                .andExpect(status().isNoContent());

        verify(solicitudService, times(1)).cerrarSolicitud(1L);
    }

    @Test
    void asignarTecnico_ConPayloadValido_DeberiaDevolverHttp200() throws Exception {
        String jsonPayload = "{\"tecnicoId\": 50}";

        mockMvc.perform(post("/api/solicitudes/1/tecnico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk()); // Código HTTP 200 OK

        verify(solicitudService, times(1)).asignarTecnico(1L, 50L);
    }

    // --- PASO 3.2: VERIFICAR MANEJO DE ERRORES ADECUADO (Sin ControllerAdvice) ---

    @Test
    void asignarTecnico_ConIdTecnicoNulo_DeberiaDevolverHttp400BadRequest() throws Exception {
        String jsonPayload = "{\"tecnicoId\": null}"; // Rompe la regla @NotNull del RequestDTO

        mockMvc.perform(post("/api/solicitudes/1/tecnico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest()); // Interceptado de forma nativa por @Valid de Spring -> 400

        verifyNoInteractions(solicitudService); // Asegura que el error frenó la petición antes del servicio
    }

    @Test
    void consultar_SiNoExiste_DeberiaDevolverHttp404NotFound() throws Exception {
        // Simulamos que el servicio arroja la excepción que configuramos con @ResponseStatus
        when(solicitudService.consultarSolicitud(99L))
                .thenThrow(new EntidadNoEncontrada("Solicitud no encontrada"));

        mockMvc.perform(get("/api/solicitudes/99"))
                .andExpect(status().isNotFound()); // Responde 404 automáticamente gracias al @ResponseStatus
    }

    @Test
    void cerrar_SiViolaReglaDeNegocio_DeberiaDevolverHttp400BadRequest() throws Exception {
        // Simulamos la violación de regla de negocio
        doThrow(new ReglaNegocio("No se puede cerrar"))
                .when(solicitudService).cerrarSolicitud(1L);

        mockMvc.perform(put("/api/solicitudes/1/cerrar"))
                .andExpect(status().isBadRequest()); // Responde 400 automáticamente gracias al @ResponseStatus
    }
    
    @Test
    void consultar_SinClienteNiTecnico_DeberiaMapearNullsCorrectamente() throws Exception {
        Solicitud solicitudPrueba = new Solicitud();
        solicitudPrueba.setId(2L);
        solicitudPrueba.setTecnico(null); // Forzamos que sea null
        solicitudPrueba.setCliente(null); // Forzamos que sea null

        when(solicitudService.consultarSolicitud(2L)).thenReturn(solicitudPrueba);

        mockMvc.perform(get("/api/solicitudes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente").isEmpty())
                .andExpect(jsonPath("$.tecnicoId").isEmpty());
    }
    
    @Test
    void consultar_ConClienteYTecnicoAsignados_DeberiaMapearTodo() throws Exception {
        Solicitud sol = new Solicitud();
        sol.setId(3L);
        sol.setDescripcion("Test completo");
        sol.setTecnico(new Tecnico(5L, true));
        com.mgcss.domain.Cliente cliente = new com.mgcss.domain.Cliente(10L, "Acme");
        sol.setCliente(cliente);

        when(solicitudService.consultarSolicitud(3L)).thenReturn(sol);

        mockMvc.perform(get("/api/solicitudes/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tecnicoId").value(5))
                .andExpect(jsonPath("$.cliente.id").value(10))
                .andExpect(jsonPath("$.cliente.nombre").value("Acme"));
    }
}