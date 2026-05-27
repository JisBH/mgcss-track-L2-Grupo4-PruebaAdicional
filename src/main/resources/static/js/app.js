let currentRole = "ADMIN";
let currentTecnicoId = null;

// Inicialización cuando carga la página
document.addEventListener('DOMContentLoaded', () => {
    initData();
});

async function initData() {
    await cargarTecnicos();
    await cargarSolicitudes();
    switchRole();
}

// Cambiar de Rol (Simulador de Sesión)
function switchRole() {
    const roleValue = document.getElementById('userRole').value;
    if (roleValue === "ADMIN") {
        currentRole = "ADMIN";
        currentTecnicoId = null;
    } else {
        currentRole = "TECNICO";
        currentTecnicoId = parseInt(roleValue);
    }

    // Mostrar/Ocultar elementos según rol
    document.querySelectorAll('.admin-only').forEach(el => el.style.display = (currentRole === 'ADMIN' ? 'inline-block' : 'none'));
    cargarSolicitudes(); // Recargar tabla para ajustar botones de acción
}

// --- LLAMADAS A LA API ---

async function cargarTecnicos() {
    const response = await fetch('/api/tecnicos');
    const tecnicos = await response.json();
    const selectRole = document.getElementById('userRole');
    
    // Limpiar opciones de técnicos, mantener Admin
    selectRole.innerHTML = '<option value="ADMIN">Administrador</option>';
    
    tecnicos.forEach(t => {
        const opt = document.createElement('option');
        opt.value = t.id;
        opt.textContent = `Técnico ID: ${t.id} (${t.activo ? 'Activo' : 'Inactivo'})`;
        selectRole.appendChild(opt);
    });
}

async function cargarSolicitudes() {
    const response = await fetch('/api/solicitudes');
    const solicitudes = await response.json();
    const tableBody = document.getElementById('solicitudesTable');
    tableBody.innerHTML = '';

    solicitudes.forEach(s => {
        const row = document.createElement('tr');
        
        // 1. Lógica de botones según rol y estado (Tu código original)
        let acciones = '';
        
        if (currentRole === 'ADMIN') {
            if (!s.tecnicoId) {
                acciones = `<select onchange="asignarTecnico(${s.id}, this.value)">
                                <option value="">Asignar técnico...</option>
                                ${Array.from(document.getElementById('userRole').options)
                                    .filter(opt => opt.value !== "ADMIN")
                                    .map(opt => `<option value="${opt.value}">${opt.textContent}</option>`).join('')}
                            </select>`;
            } else if (s.estado === 'CERRADA') {
                acciones = `<button class="btn-warning" onclick="reabrir(${s.id})">Reabrir</button>`;
            } else {
                acciones = `<span style="color: var(--secondary)">En gestión</span>`;
            }
        } else {
            // Si es TÉCNICO y tiene la tarea asignada
            if (s.tecnicoId === currentTecnicoId) {
                if (s.estado === 'ABIERTA') {
                    acciones = `<button class="btn-success" onclick="cambiarEstado(${s.id})">Iniciar</button>`;
                } else if (s.estado === 'EN_PROCESO') {
                    acciones = `<button class="btn-primary" onclick="cerrarSolicitud(${s.id})">Cerrar Tarea</button>`;
                }
            }
        }

        // 2. Extraer el nombre del cliente con seguridad (por si alguna tarea antigua no tiene cliente)
        const clienteAsignado = s.cliente ? s.cliente.nombre : '<em style="color:gray;">Sin asignar</em>';
        // (Opcional) Si en tu DTO devuelves clienteNombre directo en vez del objeto anidado, sería: s.clienteNombre

        // 3. Pintar la fila completa unificada (con tus 6 columnas en el orden correcto)
        row.innerHTML = `
            <td>#${s.id}</td>
            <td>${s.descripcion || s.fechaCreacion || 'Ver detalle'}</td>
            <td><strong>${clienteAsignado}</strong></td> <td><span class="badge badge-${s.estado.toLowerCase()}">${s.estado}</span></td>
            <td>${s.tecnicoId ? '👤 Técnico ' + s.tecnicoId : '<i>Sin asignar</i>'}</td>
            <td>${acciones}</td>
        `;
        
        tableBody.appendChild(row);
    });
}

async function crearSolicitud() {
    await fetch('/api/solicitudes', { method: 'POST' });
    cargarSolicitudes();
}

async function crearTecnico() {
    await fetch('/api/tecnicos', { method: 'POST' });
    cargarTecnicos();
}

async function asignarTecnico(solicitudId, tecnicoId) {
    if (!tecnicoId) return;
    await fetch(`/api/solicitudes/${solicitudId}/tecnico`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ tecnicoId: parseInt(tecnicoId) })
    });
    cargarSolicitudes();
}

async function cambiarEstado(id) {
    await fetch(`/api/solicitudes/${id}/estado`, { method: 'PUT' });
    cargarSolicitudes();
}

async function reabrir(id) {
    await fetch(`/api/solicitudes/${id}/reabrir`, { method: 'PATCH' });
    cargarSolicitudes();
}

/**
 * Llama al endpoint de cierre de solicitud y refresca la interfaz.
 * @param {number} id - ID de la solicitud a cerrar.
 */
async function cerrarSolicitud(id) {
    if (!confirm('¿Estás seguro de que deseas dar por finalizada esta tarea?')) return;
    
    try {
        const response = await fetch(`/api/solicitudes/${id}/cerrar`, { 
            method: 'PUT' 
        });

        if (response.ok) {
            cargarSolicitudes(); // Refresca la tabla
        } else {
            // Manejo de errores de reglas de negocio
            const errorData = await response.json();
            alert("No se pudo cerrar: " + (errorData.message || "Error desconocido"));
        }
    } catch (error) {
        console.error("Error en la petición:", error);
        alert("Error de conexión con el servidor");
    }
}

function solicitarNombreCliente() {
    const nombre = prompt("Introduce el nombre del nuevo cliente:");
    if (nombre && nombre.trim() !== "") {
        crearCliente(nombre.trim());
    }
}

async function crearCliente(nombreCliente) {
    try {
        const response = await fetch('/api/clientes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombre: nombreCliente })
        });
        
        if (response.ok) {
            alert("Cliente registrado con éxito");
            // Aquí puedes llamar a una función para refrescar selects si asocias clientes a solicitudes
        } else {
            alert("Error al registrar el cliente");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}

/**
 * Muestra el modal de nueva solicitud y carga la lista de clientes disponibles.
 */
async function abrirModalSolicitud() {
    document.getElementById('modalSolicitud').style.display = 'flex';
    
    const selector = document.getElementById('solicitudCliente');
    if (!selector) return;

    try {
        const response = await fetch('/api/clientes');
        if (response.ok) {
            const clientes = await response.json();
            
            // Limpiamos opciones previas e insertamos la opción por defecto
            selector.innerHTML = '<option value="">-- Selecciona un Cliente --</option>';
            
            // Rellenamos el desplegable con los IDs y Nombres del backend
            clientes.forEach(cliente => {
                const option = document.createElement('option');
                option.value = cliente.id; // Este ID se enviará en el DTO
                option.textContent = cliente.nombre;
                selector.appendChild(option);
            });
        }
    } catch (error) {
        console.error("Error al cargar clientes en el selector:", error);
    }
}

/**
 * Oculta el formulario modal y limpia sus campos.
 */
function cerrarModalSolicitud() {
    document.getElementById('modalSolicitud').style.display = 'none';
    document.getElementById('solicitudDescripcion').value = '';
    document.getElementById('solicitudCliente').value = '';
}

/**
 * Envía los datos del formulario al backend para crear la solicitud con su cliente asociado.
 */
async function guardarNuevaSolicitud() {
    const descripcion = document.getElementById('solicitudDescripcion').value;
    const clienteId = document.getElementById('solicitudCliente').value;

    if (!descripcion.trim() || !clienteId) {
        alert("Por favor, introduce una descripción y selecciona un cliente.");
        return;
    }

    // Construimos el Payload compatible con tu SolicitudRequestDTO
    const payload = {
        descripcion: descripcion,
        clienteId: parseInt(clienteId) 
    };

    try {
        const response = await fetch('/api/solicitudes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Solicitud creada y asignada al cliente con éxito.");
            cerrarModalSolicitud();
            initData(); // Refresca la tabla principal automáticamente
        } else {
            alert("Error al procesar la creación de la solicitud.");
        }
    } catch (error) {
        console.error("Error en la petición POST:", error);
    }
}