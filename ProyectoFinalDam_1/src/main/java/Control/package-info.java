/**
 * Paquete de control de sesión de la aplicación CRISS.
 *
 * <p>Contiene {@link Control.SesionDatos}, la clase estática que actúa como
 * almacén en memoria de los objetos creados durante la ejecución actual.
 * Los datos almacenados aquí se pierden al cerrar la aplicación; no se persisten
 * entre sesiones.</p>
 *
 * <p>Emplea tres tipos de colección para cumplir el requisito del proyecto de usar
 * al menos tres colecciones distintas:</p>
 * <ul>
 *   <li>{@code TreeSet} para Alumno, Ciclo, Modulo y Matricula — orden natural.</li>
 *   <li>{@code ArrayList} para LineaMatricula — clave compuesta, sin orden natural.</li>
 *   <li>{@code LinkedHashMap} para el registro cronológico de inserciones.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package Control;
