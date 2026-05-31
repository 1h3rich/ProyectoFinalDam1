/**
 * Paquete raíz de excepciones de negocio del sistema CRISS.
 *
 * <p>Contiene las excepciones de propósito general que no están asociadas a una
 * sola entidad del modelo:</p>
 * <ul>
 *   <li>{@link excepciones.CicloExistenteException} — ciclo duplicado en la BD.</li>
 *   <li>{@link excepciones.ErrorGenerico} — error de negocio genérico no contemplado
 *       por excepciones más específicas.</li>
 *   <li>{@link excepciones.FicheroVacioException} — fichero de datos vacío al intentar leer.</li>
 *   <li>{@link excepciones.MatriculaInvalidaException} — datos de matrícula inválidos.</li>
 *   <li>{@link excepciones.YaImportadoException} — intento de importar datos ya cargados en sesión.</li>
 * </ul>
 *
 * <p>Las excepciones específicas de cada entidad se ubican en los subpaquetes:
 * {@code excepciones.Alumno}, {@code excepciones.Ciclo}, {@code excepciones.Modulo},
 * {@code excepciones.Matricula} y {@code excepciones.LineaMatricula}.</p>
 *
 * @author isard
 * @version 1.0
 */
package excepciones;
