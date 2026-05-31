/**
 * Excepciones de validación específicas de la entidad {@link modelos.Matricula}.
 *
 * <p>Cubren los cuatro campos de negocio de la matrícula:</p>
 * <ul>
 *   <li>{@link excepciones.Matricula.AñoAcademicoException} — año fuera de [1900, 3000].</li>
 *   <li>{@link excepciones.Matricula.EstadoNoValidoException} — estado no es "Activa", "Parcial" ni "Anulada".</li>
 *   <li>{@link excepciones.Matricula.ImporteNoValidoException} — importe negativo.</li>
 *   <li>{@link excepciones.Matricula.LineaInvalidaMatriculaException} — línea CSV con campo count ≠ 5.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package excepciones.Matricula;
