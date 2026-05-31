/**
 * Paquete de formularios de creación de entidades del sistema CRISS.
 *
 * <p>Cada JFrame de este paquete recoge los datos de una nueva entidad a través
 * de un formulario Swing, valida la entrada y ejecuta el INSERT correspondiente en
 * la base de datos. Al guardar con éxito, registra el objeto en {@link Control.SesionDatos}
 * para que aparezca en la pantalla de registro de sesión.</p>
 * <ul>
 *   <li>{@link pantallas.Crear.CrearAlumno} — da de alta un alumno y abre automáticamente
 *       el formulario de matrícula.</li>
 *   <li>{@link pantallas.Crear.CrearCiclo} — da de alta un ciclo formativo.</li>
 *   <li>{@link pantallas.Crear.CrearModulo} — da de alta un módulo asociado a un ciclo.</li>
 *   <li>{@link pantallas.Crear.CrearMatricula} — da de alta una matrícula para un alumno.</li>
 *   <li>{@link pantallas.Crear.CrearLineaMatricula} — da de alta una línea de matrícula
 *       (módulo dentro de una matrícula).</li>
 *   <li>{@link pantallas.Crear.GestionarModulosCiclo} — reasigna módulos entre ciclos.</li>
 * </ul>
 *
 * @author isard
 * @version 1.0
 */
package pantallas.Crear;
