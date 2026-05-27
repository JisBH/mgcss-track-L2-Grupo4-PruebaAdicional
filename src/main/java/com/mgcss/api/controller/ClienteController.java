package com.mgcss.api.controller;

import com.mgcss.api.dto.ClienteRequestDTO;
import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de la API REST encargado de exponer los endpoints públicos
 * para la interacción y administración de los recursos del tipo Cliente.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Construye el controlador asociándolo con su capa de servicio inmediata.
     * * @param clienteService Servicio de aplicación para Clientes.
     */
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Endpoint GET para consultar el listado completo de clientes.
     * * @return {@link ResponseEntity} con la lista de clientes y código HTTP 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    /**
     * Endpoint POST para registrar un nuevo cliente con validación de restricciones JSR-380.
     * * @param request DTO validado que contiene los datos del nuevo cliente.
     * @return {@link ResponseEntity} con el cliente creado y código HTTP 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteRequestDTO request) {
        Cliente nuevo = clienteService.crearCliente(request.nombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}