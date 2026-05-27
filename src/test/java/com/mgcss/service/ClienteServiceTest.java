package com.mgcss.service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de aislamiento sobre {@link ClienteService} utilizando 
 * Mockito para simular el comportamiento del puerto de salida.
 */
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepoMock;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void crearCliente_DeberiaLlamarAlRepositorio() {
        Cliente clienteGuardado = new Cliente(1L, "Cliente Persistido");
        when(clienteRepoMock.save(any(Cliente.class))).thenReturn(clienteGuardado);

        Cliente resultado = clienteService.crearCliente("Cliente Persistido");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(clienteRepoMock, times(1)).save(any(Cliente.class));
    }
}