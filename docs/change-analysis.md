# Change Analysis - Reopen and History

1. ¿Qué métodos del dominio se ven afectados?
   - El constructor (para inicializar el historial con ABIERTA).
   - 'iniciarProceso()' y 'cerrar()'  (deben registrar el cambio en el historial).
   - Nuevo método: 'reabrir()'.

2. ¿Qué reglas actuales cambian?
   - Antes 'CERRADA' era un estado final. Ahora, desde 'CERRADA' se permite la transición hacia 'EN_PROCESO'.

3. ¿Qué tests deberían romperse?
   - En teoría, ninguno de los anteriores debería romperse si añadimos el historial como una extensión pasiva. 			Romperíamos tests si alteramos las reglas de 'cerrar()' o 'asignarTecnico()'.

4. ¿Qué parte del modelo debe extenderse?
   - La entidad 'Solicitud necesitará un atributo 'List<Estado> historialEstados'. se ha decidido usar una lista 		de Enum 'Estado' porque es simple, expresivo y evita crear una entidad separada innecesaria.

5. ¿Qué impacto tiene en persistencia?
   - 'SolicitudEntity' requerirá una colección mapeada con '@ElementCollection' para guardar la lista de Enums
    		en una tabla secundaria automática.
   
  