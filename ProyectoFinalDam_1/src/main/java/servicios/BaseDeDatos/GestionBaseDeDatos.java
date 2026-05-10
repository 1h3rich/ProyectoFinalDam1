package servicios.BaseDeDatos;

import Config.Config;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.*;

public class GestionBaseDeDatos {

    private static Connection con;

    public static ArrayList<Alumno> listaAlumno = new ArrayList<>();
    public static ArrayList<Matricula> listaMatricula = new ArrayList<>();
    public static ArrayList<LineaMatricula> listaLineaMatricula = new ArrayList<>();
    public static ArrayList<Ciclo> listaCiclo = new ArrayList<>();
    public static ArrayList<Modulo> listaModulo = new ArrayList<>();

    /**
     * Conecta Java con la base de datos MySQL.
     */
    public static void vincularBDD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");

            try {
                con = DriverManager.getConnection(
                        Config.urlSQL,
                        Config.nombreUsuarioSQL,
                        Config.contraseñaSQL[0]
                );
                System.out.println("Conexion exitosa");

            } catch (SQLException e) {
                try {
                    con = DriverManager.getConnection(
                            Config.urlSQL,
                            Config.nombreUsuarioSQL,
                            Config.contraseñaSQL[1]
                    );
                    System.out.println("Conexion exitosa");

                } catch (SQLException ex) {
                    System.out.println("Error al conectar con la base de datos");
                    Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Error: no se encontro el driver de MySQL");
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la conexion si esta abierta.
     */
    public static void cerrarBDD() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexion cerrada correctamente");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Comprueba que haya conexion antes de ejecutar consultas.
     */
    private static void comprobarConexion() throws SQLException {
        if (con == null || con.isClosed()) {
            throw new SQLException("No hay conexion abierta con la base de datos");
        }
    }

    /**
     * Ejecuta consultas SELECT.Si guardarDatos es false:
     *
     * datosConsulta[0] = consulta SQL datosConsulta[1...] = columnas a mostrar
     *
     * Ejemplo: {"SELECT * FROM alumno", "codigo", "nombre", "apellido"}
     *
     * Si guardarDatos es true:
     *
     * datosConsulta[0] = nombre del objeto datosConsulta[1] = consulta SQL
     * datosConsulta[2...] = columnas para crear el objeto
     *
     * Ejemplo: {"Alumno", "SELECT * FROM alumno", "codigo", "nombre",
     * "apellido"}
     *
     * @param datosConsulta
     * @param entradas
     * @param mostrarPorPantalla
     * @param guardarDatos
     */
    public static void realizarSQL(String[] datosConsulta, String[] entradas, boolean mostrarPorPantalla, boolean guardarDatos) {
        try {
            comprobarConexion();

            int posicionSQL = guardarDatos ? 1 : 0;
            int primeraColumna = guardarDatos ? 2 : 1;

            String sql = datosConsulta[posicionSQL];

            try ( PreparedStatement pst = con.prepareStatement(sql)) {

                if (entradas != null) {
                    for (int i = 0; i < entradas.length; i++) {
                        pst.setString(i + 1, entradas[i]);
                    }
                }

                try ( ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {

                        if (mostrarPorPantalla) {
                            for (int i = primeraColumna; i < datosConsulta.length; i++) {
                                System.out.print(rs.getString(datosConsulta[i]) + " ");
                            }
                            System.out.println();
                        }

                        if (guardarDatos) {
                            String[] objeto = new String[datosConsulta.length - primeraColumna];

                            for (int i = primeraColumna, j = 0; i < datosConsulta.length; i++, j++) {
                                objeto[j] = rs.getString(datosConsulta[i]);
                            }

                            guardarObjeto(datosConsulta[0], objeto);
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Guarda el objeto en su lista correspondiente.
     */
    private static void guardarObjeto(String tipoObjeto, String[] datos) {
        if (tipoObjeto.equalsIgnoreCase("Alumno")) {
            Alumno alumno = new Alumno(datos);
            listaAlumno.add(alumno);

        } else if (tipoObjeto.equalsIgnoreCase("Ciclo")) {
            Ciclo ciclo = new Ciclo(datos);
            listaCiclo.add(ciclo);

        } else if (tipoObjeto.equalsIgnoreCase("LineaMatricula")) {
            LineaMatricula lineaMatricula = new LineaMatricula(datos);
            listaLineaMatricula.add(lineaMatricula);

        } else if (tipoObjeto.equalsIgnoreCase("Matricula")) {
            Matricula matricula = new Matricula(datos);
            listaMatricula.add(matricula);

        } else if (tipoObjeto.equalsIgnoreCase("Modulo")) {
            Modulo modulo = new Modulo(datos);
            listaModulo.add(modulo);

        } else {
            System.out.println("Tipo de objeto no reconocido: " + tipoObjeto);
        }
    }

    /**
     * Inserta datos en la base de datos. Se usa para INSERT.
     *
     * @param datosInsertar
     * @param entradas
     */
    public static void insertarDatos(String[] datosInsertar, String[] entradas) {
        ejecutarActualizacion(datosInsertar[0], entradas, "Filas insertadas");
    }

    /**
     * Actualiza filas de la base de datos. Se usa para UPDATE.
     *
     * @param datosActualizacion
     * @param entradas
     */
    public static void actualizarFila(String[] datosActualizacion, String[] entradas) {
        ejecutarActualizacion(datosActualizacion[0], entradas, "Filas actualizadas");
    }

    /**
     * Elimina filas de la base de datos.Se usa para DELETE.
     *
     * @param datosEliminar
     * @param entradas
     */
    public static void eliminarFila(String[] datosEliminar, String[] entradas) {
        ejecutarActualizacion(datosEliminar[0], entradas, "Filas eliminadas");
    }

    /**
     * Metodo comun para INSERT, UPDATE y DELETE.
     *
     * Estos comandos NO devuelven ResultSet. Por eso se usa executeUpdate().
     */
    private static void ejecutarActualizacion(String sql, String[] entradas, String mensaje) {
        try {
            comprobarConexion();

            try ( PreparedStatement pst = con.prepareStatement(sql)) {

                if (entradas != null) {
                    for (int i = 0; i < entradas.length; i++) {
                        pst.setString(i + 1, entradas[i]);
                    }
                }

                int filasAfectadas = pst.executeUpdate();
                System.out.println(mensaje + ": " + filasAfectadas);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
