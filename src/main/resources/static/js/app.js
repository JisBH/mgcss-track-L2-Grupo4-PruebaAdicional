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
        
        // Lógica de botones según rol y estado
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

        row.innerHTML = `
            <td>#${s.id}</td>
            <td>${s.fechaCreacion}</td>
            <td><span class="badge badge-${s.estado.toLowerCase()}">${s.estado}</span></td>
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