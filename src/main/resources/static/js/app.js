// URL base de tu API (Cámbiala si tu controlador usa otra ruta)
const API_URL = '/api/solicitudes';

// 1. Función para la página index.html (Cargar lista)
function cargarSolicitudes() {
    const tbody = document.getElementById('tabla-body');
    if (!tbody) return; // Si no estamos en el index, no hace nada

    tbody.innerHTML = '<tr><td colspan="4" class="text-center">Cargando datos...</td></tr>';

    fetch(API_URL)
        .then(response => response.json())
        .then(data => {
            tbody.innerHTML = ''; 
            if(data.length === 0) {
                tbody.innerHTML = '<tr><td colspan="4" class="text-center text-muted">No hay solicitudes registradas</td></tr>';
                return;
            }

            data.forEach(solicitud => {
                const fila = `
                    <tr>
                        <td class="fw-bold">#${solicitud.id}</td>
                        <td>${solicitud.descripcion}</td>
                        <td>
                            <span class="badge ${solicitud.estado === 'CREADA' ? 'bg-primary' : 'bg-warning text-dark'}">
                                ${solicitud.estado || 'CREADA'}
                            </span>
                        </td>
                        <td>${solicitud.tecnicoId ? 'Técnico ' + solicitud.tecnicoId : '<span class="text-muted">Sin asignar</span>'}</td>
                    </tr>
                `;
                tbody.innerHTML += fila;
            });
        })
        .catch(error => {
            console.error('Error:', error);
            tbody.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Error al conectar con el servidor</td></tr>';
        });
}

// 2. Función para la página nueva.html (Guardar formulario)
function guardarSolicitud(event) {
    event.preventDefault(); // Evita que la página se recargue

    const descripcion = document.getElementById('descripcion').value;
    const clienteId = document.getElementById('clienteId').value;

    const nuevaSolicitud = {
        descripcion: descripcion,
        clienteId: parseInt(clienteId)
    };

    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(nuevaSolicitud)
    })
    .then(response => {
        if(response.ok) {
            // Si va bien, volvemos a la página principal
            window.location.href = 'index.html';
        } else {
            alert("Hubo un error al crear la solicitud. Revisa los datos.");
        }
    })
    .catch(error => console.error('Error:', error));
}

// Ejecutar funciones automáticamente según la página en la que estemos
document.addEventListener("DOMContentLoaded", () => {
    cargarSolicitudes();
    
    const formNueva = document.getElementById('form-nueva-solicitud');
    if (formNueva) {
        formNueva.addEventListener('submit', guardarSolicitud);
    }
});