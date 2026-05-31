/**
 * Excepciones de validación específicas de la entidad {@link modelos.Modulo}.
 *
 * <p>Cubren los campos de negocio del módulo formativo:</p>
 * <ul>
 *   <li>{@link excepciones.Modulo.CreditosMayor0Exception} — créditos ECTS ≤ 0.</li>
 *   <li>{@link excepciones.Modulo.CursoVacioException} — curso nulo o vacío.</li>
 *   <li>{@link excepciones.Modulo.LineaInvalidaModuloException} — línea CSV con campo count ≠ 6.</li>
 *   <li>{@link excepciones.Modulo.NombreVacioException} — nombre del módulo nulo o vacío.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package excepciones.Modulo;
