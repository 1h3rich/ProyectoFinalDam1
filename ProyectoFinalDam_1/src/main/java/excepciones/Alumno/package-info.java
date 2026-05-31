/**
 * Excepciones de validación específicas de la entidad {@link modelos.Alumno}.
 *
 * <p>Cada clase de este paquete corresponde a un campo del alumno que no ha superado
 * su restricción de negocio:</p>
 * <ul>
 *   <li>{@link excepciones.Alumno.AlumnoVacioException} — nombre nulo o vacío.</li>
 *   <li>{@link excepciones.Alumno.CodigMayor0Exception} — código identificador ≤ 0.</li>
 *   <li>{@link excepciones.Alumno.CorreoNoValidoException} — formato de correo inválido.</li>
 *   <li>{@link excepciones.Alumno.DomicilioVacioException} — domicilio nulo o vacío.</li>
 *   <li>{@link excepciones.Alumno.FechaNoValidaException} — fecha nula o futura.</li>
 *   <li>{@link excepciones.Alumno.LineaInvalidaAlumnoException} — línea CSV con campo count ≠ 6.</li>
 *   <li>{@link excepciones.Alumno.TelefonoInvalidoException} — teléfono sin 9 dígitos numéricos.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package excepciones.Alumno;
