/**
 * Excepciones de validación específicas de la entidad {@link modelos.LineaMatricula}.
 *
 * <p>Una línea de matrícula tiene clave primaria compuesta (cod_matricula, cod_modulo).
 * Las excepciones de este paquete cubren los campos de negocio:</p>
 * <ul>
 *   <li>{@link excepciones.LineaMatricula.LineaInvalidaLineaMatriculaException} — CSV mal formado.</li>
 *   <li>{@link excepciones.LineaMatricula.MatriculaNotNullException} — matrícula asociada es null.</li>
 *   <li>{@link excepciones.LineaMatricula.ModuloNotNullException} — módulo asociado es null.</li>
 *   <li>{@link excepciones.LineaMatricula.PrimeraCalifException} — primera calificación fuera de [0, 10].</li>
 *   <li>{@link excepciones.LineaMatricula.RepeticionException} — número de repetición negativo.</li>
 *   <li>{@link excepciones.LineaMatricula.SegundaCalifException} — segunda calificación fuera de [0, 10].</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package excepciones.LineaMatricula;
