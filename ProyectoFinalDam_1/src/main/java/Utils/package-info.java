/**
 * Paquete de utilidades transversales del sistema CRISS.
 *
 * <p>Clases incluidas:</p>
 * <ul>
 *   <li>{@link Utils.Validadores} — métodos estáticos de validación de campos del dominio
 *       (teléfono, correo, domicilio, calificaciones, etc.).</li>
 *   <li>{@link Utils.GsonUtils} — instancia singleton de Gson preconfigurada con adaptadores
 *       para {@code java.time.LocalDate}, necesarios en Java 21.</li>
 *   <li>{@link Utils.ItemCombo} — elemento de JComboBox que asocia un {@code id} numérico
 *       con un texto visible, usado en los formularios de selección.</li>
 *   <li>{@link Utils.TipoDato} — enumeración que identifica la entidad del modelo sobre la
 *       que opera una pantalla (ALUMNO, CICLO, MODULO, MATRICULA, LINEA_MATRICULA).</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package Utils;
