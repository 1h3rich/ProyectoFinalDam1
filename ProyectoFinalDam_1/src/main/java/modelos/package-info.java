/**
 * Paquete del modelo de dominio del sistema CRISS.
 *
 * <p>Contiene las cinco entidades principales que mapean las tablas de la base de datos
 * {@code centro_formacion}. Todos los modelos implementan
 * {@link interfaces.InterpolaridadDeDatos} (persistencia en ficheros),
 * {@link java.io.Serializable} (serialización binaria) y {@link java.lang.Comparable}
 * (orden natural para {@code TreeSet}), salvo {@link modelos.LineaMatricula} que omite
 * {@code Comparable} por su clave primaria compuesta.</p>
 *
 * <table border="1">
 *   <caption>Entidades del modelo</caption>
 *   <tr><th>Clase</th><th>Tabla BD</th><th>Clave primaria</th></tr>
 *   <tr><td>{@link modelos.Alumno}</td><td>alumno</td><td>codigo (AUTO_INCREMENT)</td></tr>
 *   <tr><td>{@link modelos.Ciclo}</td><td>ciclo</td><td>codigo (AUTO_INCREMENT)</td></tr>
 *   <tr><td>{@link modelos.Modulo}</td><td>modulo</td><td>codigo (AUTO_INCREMENT)</td></tr>
 *   <tr><td>{@link modelos.Matricula}</td><td>matricula</td><td>codigo (AUTO_INCREMENT)</td></tr>
 *   <tr><td>{@link modelos.LineaMatricula}</td><td>linea_matricula</td><td>codigo_matricula + codigo_modulo</td></tr>
 * </table>
 *
 * @author isard
 * @version 1.0
 */
package modelos;
