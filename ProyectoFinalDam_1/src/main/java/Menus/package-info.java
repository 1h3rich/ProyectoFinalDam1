/**
 * Paquete de menús de consola del sistema CRISS.
 *
 * <p>Cada clase de este paquete implementa el CRUD completo de una entidad a través
 * de un bucle interactivo por consola, compartiendo el {@code Scanner} de la clase
 * principal para evitar cierres prematuros del flujo de entrada estándar.
 * También exponen los métodos estáticos {@code importar} y {@code exportar} que son
 * invocados por las pantallas Swing de importación/exportación.</p>
 *
 * <p>Clases incluidas:</p>
 * <ul>
 *   <li>{@link Menus.MenuAlumno} — gestión de la tabla alumno.</li>
 *   <li>{@link Menus.MenuCiclo} — gestión de la tabla ciclo.</li>
 *   <li>{@link Menus.MenuModulo} — gestión de la tabla modulo.</li>
 *   <li>{@link Menus.MenuMatricula} — gestión de la tabla matricula.</li>
 *   <li>{@link Menus.MenuLineaMatricula} — gestión de la tabla linea_matricula.</li>
 *   <li>{@link Menus.MenuConsultas} — ejecución de las 9 consultas multitabla predefinidas.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package Menus;
