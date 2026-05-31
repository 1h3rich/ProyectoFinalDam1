/**
 * Paquete de pantallas de exportación e importación masiva del sistema CRISS.
 *
 * <p>Contiene los JFrame que permiten volcar o cargar en lote los datos de cualquier
 * entidad del modelo a/desde fichero (TXT, CSV, JSON o binario serializado).</p>
 * <ul>
 *   <li>{@link pantallas.Concurrencia.Exportar} — selecciona la entidad y el formato de salida,
 *       y escribe el fichero correspondiente.</li>
 *   <li>{@link pantallas.Concurrencia.Importar} — selecciona la entidad y el formato de entrada,
 *       valida la estructura del fichero e inserta los registros en la base de datos.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package pantallas.Concurrencia;
