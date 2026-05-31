/**
 * Excepciones de validación específicas de la entidad {@link modelos.Ciclo}.
 *
 * <p>Cada clase corresponde a un campo del ciclo que no cumple sus restricciones:</p>
 * <ul>
 *   <li>{@link excepciones.Ciclo.AñoNoValidoException} — año del currículum fuera de [1900, 3000].</li>
 *   <li>{@link excepciones.Ciclo.DenominacionVaciaException} — denominación nula o vacía.</li>
 *   <li>{@link excepciones.Ciclo.FamiliaVaciaException} — familia profesional nula o vacía.</li>
 *   <li>{@link excepciones.Ciclo.HorasMayor0Exception} — horas totales ≤ 0.</li>
 *   <li>{@link excepciones.Ciclo.LineaInvalidaCicloException} — línea CSV con campo count ≠ 6.</li>
 *   <li>{@link excepciones.Ciclo.NivelVacioException} — nivel nulo, vacío o no reconocido.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package excepciones.Ciclo;
