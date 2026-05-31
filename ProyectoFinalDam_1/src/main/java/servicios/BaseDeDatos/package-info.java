/**
 * Paquete de acceso a datos (capa de persistencia en BD) del sistema CRISS.
 *
 * <p>Contiene tres clases estáticas que cubren la totalidad de la interacción con
 * la base de datos MySQL {@code centro_formacion}:</p>
 * <ul>
 *   <li>{@link servicios.BaseDeDatos.GestionBaseDeDatos} — gestiona la conexión JDBC,
 *       proporciona métodos para CRUD, consultas, transacciones y construcción de
 *       {@code DefaultTableModel} para las vistas Swing. Usa el patrón de clase de
 *       utilidad estática (no instanciable).</li>
 *   <li>{@link servicios.BaseDeDatos.ConsultasSQL} — repositorio de constantes SQL para
 *       todas las operaciones CRUD de las cinco tablas. Los arrays siguen la convención
 *       {@code [tipojava, sql, columnas...]} o {@code [sql, columnas...]}.</li>
 *   <li>{@link servicios.BaseDeDatos.ConsultasEjercicios} — sentencias SQL de los nueve
 *       ejercicios de consultas multitabla predefinidos en el enunciado del proyecto.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package servicios.BaseDeDatos;
