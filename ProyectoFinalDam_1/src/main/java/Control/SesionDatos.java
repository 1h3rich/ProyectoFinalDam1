package Control;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import modelos.Alumno;
import modelos.Ciclo;
import modelos.LineaMatricula;
import modelos.Matricula;
import modelos.Modulo;

/**
 * Clase contenedora que registra todos los objetos insertados durante la
 * ejecución actual de la aplicación.
 *
 * Usa tres tipos de colecciones distintos para cumplir el requisito del
 * proyecto de utilizar al menos tres colecciones diferentes:
 *
 * - TreeSet → listaCiclos y listaModulos Mantiene los elementos ordenados
 * automáticamente (los modelos implementan Comparable).
 *
 * - ArrayList → listaLineasMatricula Permite duplicados y acceso por índice.
 *
 * - LinkedHashMap → registroSesion Clave = "Alumno#3", valor = resumen en
 * texto. Mantiene el orden de inserción y permite buscar por clave. Se usa para
 * la pantalla "Ver datos insertados en sesión".
 *
 * Los datos se pierden al cerrar la aplicación (son de sesión). No persisten
 * entre ejecuciones.
 *
 * Uso desde una pantalla Swing de creación: int id =
 * GestionBaseDeDatos.insertarYDevolverID(sql, params); if (id != -1) { Alumno a
 * = new Alumno(id, nombre, fecha, domicilio, telefono, correo);
 * SesionDatos.registrarAlumno(a); }
 *
 * @author 1DAM
 */
public class SesionDatos {

    // =========================================================
    // ======= COLECCIÓN 1: TreeSet (orden natural) ============
    // =========================================================
    /**
     * Ciclos insertados en la base de datos.
     */
    private static final TreeSet<Ciclo> listaCiclos = new TreeSet<>();

    /**
     * Módulos insertados en la base de datos.
     */
    private static final TreeSet<Modulo> listaModulos = new TreeSet<>();

    /**
     * Alumnos insertados en la base de datos.
     */
    private static final TreeSet<Alumno> listaAlumnos = new TreeSet<>();

    /**
     * Matrículas insertadas en la base de datos.
     */
    private static final TreeSet<Matricula> listaMatriculas = new TreeSet<>();

    // =========================================================
    // ======= COLECCIÓN 2: ArrayList (acceso por índice) ======
    // =========================================================
    /**
     * Líneas de matrícula insertadas en la base de datos. Usa ArrayList porque
     * línea_matrícula tiene clave compuesta y no puede implementar un
     * Comparable sencillo.
     */
    private static final ArrayList<LineaMatricula> listaLineasMatricula = new ArrayList<>();

    // =========================================================
    // ======= GETTERS DE LAS COLECCIONES ======================
    // =========================================================
    /**
     * Devuelve la colección de ciclos de la sesión actual.
     *
     * @return TreeSet con los ciclos insertados durante la ejecución.
     */
    public static TreeSet<Ciclo> getListaCiclos() {
        return listaCiclos;
    }

    /**
     * Devuelve la colección de módulos de la sesión actual.
     *
     * @return TreeSet con los módulos insertados durante la ejecución.
     */
    public static TreeSet<Modulo> getListaModulos() {
        return listaModulos;
    }

    /**
     * Devuelve la colección de alumnos de la sesión actual.
     *
     * @return TreeSet con los alumnos insertados durante la ejecución.
     */
    public static TreeSet<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    /**
     * Devuelve la colección de matrículas de la sesión actual.
     *
     * @return TreeSet con las matrículas insertadas durante la ejecución.
     */
    public static TreeSet<Matricula> getListaMatriculas() {
        return listaMatriculas;
    }

    /**
     * Devuelve la colección de líneas de matrícula de la sesión actual.
     *
     * @return ArrayList con las líneas de matrícula insertadas durante la ejecución.
     */
    public static ArrayList<LineaMatricula> getListaLineasMatricula() {
        return listaLineasMatricula;
    }

    // =========================================================
    // ======= COLECCIÓN 3: LinkedHashMap (orden inserción) ====
    // =========================================================
    /**
     * Registro cronológico de todo lo insertado en esta sesión.
     *
     * Clave → "NombreClase#id" (ej: "Alumno#3", "Ciclo#1") Valor → descripción
     * legible del objeto insertado
     *
     * LinkedHashMap preserva el orden de inserción, lo que permite mostrar al
     * usuario los registros en el orden en que los creó.
     */
    private static final LinkedHashMap<String, String> registroSesion = new LinkedHashMap<>();

    // =========================================================
    // ============= MÉTODOS DE REGISTRO =======================
    // =========================================================
    /**
     * Registra un alumno insertado en esta sesión.
     *
     * @param alumno Alumno recién insertado con su ID ya asignado por la BD.
     * @param datosBaseDeDatos
     */
    public static void registrarAlumno(Alumno alumno, boolean datosBaseDeDatos) {
        listaAlumnos.add(alumno);

        if (!datosBaseDeDatos) { //Unicamente se guardan en la sesion si son datos insertados y no datos provenientes de la Base De Datos
            registroSesion.put(
                    "Alumno#" + alumno.getCodigo(),
                    "ALUMNO  | ID: " + alumno.getCodigo()
                    + " | Nombre: " + alumno.getNombre()
                    + " | Correo: " + alumno.getCorreo()
            );
        }
    }

    /**
     * Registra un ciclo insertado en esta sesión.
     *
     * @param ciclo Ciclo recién insertado con su ID ya asignado por la BD.
     * @param datosBaseDeDatos
     */
    public static void registrarCiclo(Ciclo ciclo, boolean datosBaseDeDatos) {
        listaCiclos.add(ciclo);

        if (!datosBaseDeDatos) {//Unicamente se guardan en la sesion si son datos insertados y no datos provenientes de la Base De Datos
            registroSesion.put(
                    "Ciclo#" + ciclo.getCodigo(),
                    "CICLO   | ID: " + ciclo.getCodigo()
                    + " | Denominación: " + ciclo.getDenominacion()
                    + " | Nivel: " + ciclo.getNivel()
            );
        }
    }

    /**
     * Registra un módulo insertado en esta sesión.
     *
     * @param modulo Módulo recién insertado con su ID ya asignado por la BD.
     * @param datosBaseDeDatos
     */
    public static void registrarModulo(Modulo modulo, boolean datosBaseDeDatos) {
        listaModulos.add(modulo);

        if (!datosBaseDeDatos) {//Unicamente se guardan en la sesion si son datos insertados y no datos provenientes de la Base De Datos
            registroSesion.put(
                    "Modulo#" + modulo.getCodigo(),
                    "MÓDULO  | ID: " + modulo.getCodigo()
                    + " | Nombre: " + modulo.getNombre()
                    + " | CicloID: " + modulo.getCodigo_ciclo()
            );
        }
    }

    /**
     * Registra una matrícula insertada en esta sesión.
     *
     * @param matricula Matrícula recién insertada con su ID ya asignado por la
     * BD.
     * @param datosBaseDeDatos
     */
    public static void registrarMatricula(Matricula matricula, boolean datosBaseDeDatos) {
        listaMatriculas.add(matricula);

        if (!datosBaseDeDatos) {//Unicamente se guardan en la sesion si son datos insertados y no datos provenientes de la Base De Datos
            registroSesion.put(
                    "Matricula#" + matricula.getCodigo(),
                    "MATRÍCLA| ID: " + matricula.getCodigo()
                    + " | AlumnoID: " + matricula.getCodigo_alumno()
                    + " | Estado: " + matricula.getEstado()
                    + " | Importe: " + matricula.getImporte()
            );
        }
    }

    /**
     * Registra una línea de matrícula insertada en esta sesión.
     *
     * @param linea LineaMatricula recién insertada.
     * @param datosBaseDeDatos
     */
    public static void registrarLineaMatricula(LineaMatricula linea, boolean datosBaseDeDatos) {
        listaLineasMatricula.add(linea);

        if (!datosBaseDeDatos) {//Unicamente se guardan en la sesion si son datos insertados y no datos provenientes de la Base De Datos
            registroSesion.put(
                    "Linea#" + linea.getCod_matricula() + "_" + linea.getCod_modulo(),
                    "LÍNEA   | MatrículaID: " + linea.getCod_matricula()
                    + " | MóduloID: " + linea.getCod_modulo()
                    + " | Repetición: " + linea.getRepeticion()
            );
        }
    }

   

    // =========================================================
    // ============= VER REGISTRO COMPLETO =====================
    // =========================================================
    /**
     * Devuelve el registro completo de todo lo insertado en esta sesión, en
     * orden cronológico (LinkedHashMap preserva el orden de inserción).
     *
     * @return LinkedHashMap con clave "TipoClase#id" y valor descripción
     * legible.
     */
    // ESTE METODO NO SE UTILIZA NUNCA
    
    public static LinkedHashMap<String, String> getRegistroCompleto() {
        return new LinkedHashMap<>(registroSesion);
    }

    /**
     * Devuelve el número total de registros insertados en esta sesión sumando
     * todos los tipos.
     *
     * @return Total de inserciones realizadas desde que arrancó la aplicación.
     */
    
    
    
    public static int getTotalInsertados() {
        return registroSesion.size();
    }

    
   
    /**
     * Indica si no se ha insertado nada en esta sesión.
     *
     * @return true si el registro de sesión está vacío.
     */
   
    public static boolean isEmpty() {
        return registroSesion.isEmpty();
    }
  
}
