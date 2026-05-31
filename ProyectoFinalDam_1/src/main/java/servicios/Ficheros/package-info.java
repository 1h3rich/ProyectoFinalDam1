/**
 * Paquete de gestión de ficheros del sistema CRISS.
 *
 * <p>Contiene {@link servicios.Ficheros.GestionFicheros}, clase estática que encapsula
 * todas las operaciones de lectura y escritura de ficheros en los cuatro formatos
 * admitidos por la aplicación:</p>
 * <ul>
 *   <li><b>TXT / CSV</b> — texto plano con separadores (TXT usa «;», CSV usa «:»).</li>
 *   <li><b>JSON</b> — un objeto JSON por línea, usando la instancia de {@link Utils.GsonUtils}.</li>
 *   <li><b>Binario</b> — colección Java serializada en fichero {@code .dat}.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package servicios.Ficheros;
