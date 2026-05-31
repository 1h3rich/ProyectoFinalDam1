/**
 * Paquete de pantallas de eliminación de entidades del sistema CRISS.
 *
 * <p>Cada JFrame de este paquete permite localizar un registro existente
 * (por teléfono+correo, código, etc.) y eliminarlo de la base de datos.
 * Las eliminaciones en cascada están definidas a nivel de BD con {@code ON DELETE CASCADE}
 * para mantener la integridad referencial.</p>
 * <ul>
 *   <li>{@link pantallas.Eliminar.MenuBorrar} — menú de selección que abre el
 *       formulario de borrado correspondiente a la entidad elegida.</li>
 *   <li>{@link pantallas.Eliminar.EliminarAlumno} — localiza y elimina un alumno
 *       junto con sus matrículas y líneas de matrícula.</li>
 *   <li>{@link pantallas.Eliminar.EliminarCiclo} — localiza y elimina un ciclo junto
 *       con sus módulos y líneas de matrícula asociadas.</li>
 *   <li>{@link pantallas.Eliminar.EliminarModulo} — elimina un módulo dado su código.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package pantallas.Eliminar;
