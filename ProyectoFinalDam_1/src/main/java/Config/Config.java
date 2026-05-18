/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

/**
 * Centraliza todos los parámetros de configuración de la aplicación:
 * credenciales de la base de datos y nombres base de los ficheros de exportación.
 *
 * @author 1DAM
 */
public class Config {
    // SQL

    /** URL JDBC de conexión a la base de datos MySQL/MariaDB. */
    public static final String urlSQL = "jdbc:mysql://127.0.0.1:3306/centro_formacion?useSSL=false&allowPublicKeyRetrieval=true";

    /** Usuario de la base de datos. */
    public static final String nombreUsuarioSQL = "root";

    /** Contraseñas a probar en orden hasta que una funcione (fallback de desarrollo). */
    public static String[] contraseñaSQL = {"alumno", "root"};
    //public static  Map<String,String > contraseñaSQL = new HashMap<>();
   

    // Ficheros (nombre base sin extensión; se le añade .csv / .json / .txt / .dat según el método)

    /** Nombre base del fichero de alumnos (sin extensión). */
    public static final String ficheroAlumno = "alumno";

    /** Nombre base del fichero de ciclos (sin extensión). */
    public static final String ficheroCiclo = "ciclo";

    /** Nombre base del fichero de módulos (sin extensión). */
    public static final String ficheroModulo = "modulo";

    /** Nombre base del fichero de líneas de matrícula (sin extensión). */
    public static final String ficheroLineaMatricula = "linea_matricula";

    /** Nombre base del fichero de matrículas (sin extensión). */
    public static final String ficheroMatricula = "matricula";

    /**
     * Devuelve la ruta completa (sin extensión) al fichero según el formato seleccionado.
     * Estructura: ficheros/FORMATO/nombreBase
     *
     * @param nombreBase Nombre base del fichero (p.ej. Config.ficheroAlumno).
     * @param formato    Formato del fichero: "CSV", "TXT", "JSON" o "BINARIO".
     * @return Ruta base sin extensión lista para pasarla a GestionFicheros.
     */
    public static String rutaFichero(String nombreBase, String formato) {
        String dir = switch (formato.toUpperCase()) {
            case "CSV"     -> "ficheros/CSV/";
            case "TXT"     -> "ficheros/TXT/";
            case "JSON"    -> "ficheros/JSON/";
            case "BINARIO" -> "ficheros/BINARIO/";
            default -> throw new IllegalArgumentException("Formato no reconocido: " + formato);
        };
        return dir + nombreBase;
    }

}
