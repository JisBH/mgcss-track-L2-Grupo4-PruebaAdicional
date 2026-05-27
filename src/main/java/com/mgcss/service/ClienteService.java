package com.mgcss.service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.EntidadNoEncontrada;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio de la capa de aplicación encargado de orquestar los casos de uso
 * relacionados con la administración y consulta de Clientes.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepo;

    /**
     * Inicializa el servicio inyectando el puerto del repositorio.
     * * @param clienteRepo El adaptador del repositorio de clientes.
     */
    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    /**
     * Caso de uso: Recuperar todos los clientes del sistema.
     * * @return Lista global de objetos {@link Cliente}.
     */
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    /**
     * Caso de uso: Registrar y persistir un nuevo cliente evaluando reglas base.
     * * @param nombre Nombre asignado al cliente.
     * @return El objeto {@link Cliente} instanciado y persistido con su ID único.
     */
    public Cliente crearCliente(String nombre) {
        Cliente nuevo = new Cliente(nombre);
        return clienteRepo.save(nuevo);
    }
    
    /**
     * Caso de uso: Buscar un cliente específico o lanzar excepción semántica en su defecto.
     * * @param id Identificador numérico del cliente a consultar.
     * @return Instancia del {@link Cliente} si es encontrado.
     * @throws EntidadNoEncontrada Si el ID no coincide con ningún registro en el sistema.
     */
    public Cliente consultarCliente(Long id) {
        return clienteRepo.findById(id)
                .orElseThrow(() -> new EntidadNoEncontrada("Cliente no encontrado con ID: " + id));
    }
}