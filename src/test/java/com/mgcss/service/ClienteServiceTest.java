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

import java.util.List;

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
    
    @Test
    void listarClientes_DeberiaDevolverLista() {
        when(clienteRepoMock.findAll()).thenReturn(List.of(new Cliente(1L, "A")));
        List<Cliente> resultado = clienteService.listarClientes();
        assertFalse(resultado.isEmpty());
    }

    @Test
    void consultarCliente_SiExiste_LoDevuelve() {
        Cliente cliente = new Cliente(1L, "A");
        when(clienteRepoMock.findById(1L)).thenReturn(java.util.Optional.of(cliente));
        Cliente encontrado = clienteService.consultarCliente(1L);
        assertEquals("A", encontrado.getNombre());
    }

    @Test
    void consultarCliente_SiNoExiste_LanzaExcepcion() {
        when(clienteRepoMock.findById(99L)).thenReturn(java.util.Optional.empty());
        assertThrows(com.mgcss.domain.EntidadNoEncontrada.class, () -> {
            clienteService.consultarCliente(99L);
        });
    }
}