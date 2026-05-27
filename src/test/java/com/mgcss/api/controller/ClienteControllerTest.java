package com.mgcss.api.controller;

import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Test
    void listarClientes_DeberiaRetornarHttp200() throws Exception {
        when(clienteService.listarClientes()).thenReturn(List.of(new Cliente(1L, "Empresa A")));
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Empresa A"));
    }

    @Test
    void crearCliente_ConPayloadValido_DeberiaRetornarHttp201() throws Exception {
        when(clienteService.crearCliente("Empresa A")).thenReturn(new Cliente(1L, "Empresa A"));
        String jsonPayload = "{\"nombre\": \"Empresa A\"}";
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Empresa A"));
    }

    @Test
    void crearCliente_ConPayloadInvalido_DeberiaRetornarHttp400() throws Exception {
        String jsonPayload = "{\"nombre\": \"\"}";
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}