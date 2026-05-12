package servicios.BaseDeDatos;

import Config.Config;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelos.*;

public class GestionBaseDeDatos {

    private static Connection con;

    public static TreeSet<Alumno> listaAlumno = new TreeSet<>();
    public static TreeSet<Matricula> listaMatricula = new TreeSet<>();
    public static TreeSet<LineaMatricula> listaLineaMatricula = new TreeSet<>();
    public static TreeSet<Ciclo> listaCiclo = new TreeSet<>();
    public static TreeSet<Modulo> listaModulo = new TreeSet<>();
    
    
    
    //En esta lista hay que guaradr los datos que insertan cada vez que se hace un insert
    public static ArrayList<String> datosInsertados = new ArrayList<>(); //Aqui no estoy seguro si es String o podria Ser de Tipo Object, si se puede elegir pondria Object
    
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
    public static void realizarConsultaSQL(String[] datosConsulta, String[] entradas, boolean mostrarPorPantalla, boolean guardarDatos) {
        try {
            comprobarConexion();

            int posicionSQL = guardarDatos ? 1 : 0; // Si hace falta guardar los datos empezara desde la posicion 1, ya que la 0 esta reservada para el tipo de dato
            int primeraColumna = guardarDatos ? 2 : 1; // Si hace falta guardar los datos , las columnas empiezan en la posicion 2 en vez de la 1 

            String sql = datosConsulta[posicionSQL];

            try ( PreparedStatement pst = con.prepareStatement(sql)) {

                if (entradas != null) {
                    for (int i = 0; i < entradas.length; i++) {
                        pst.setString(i + 1, entradas[i]); // Introducimos las entradas necesarias
                    }
                }

                try ( ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {

                        if (mostrarPorPantalla) { // Esto se podra eliminar ya que es solo para mostrar por consola
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
        
        String texto = " ";
        
        for (int i = 0; i < entradas.length; i++) {
            texto += entradas[i] + " ";
        }
        datosInsertados.add(texto);
        
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
    private static void ejecutarActualizacion(String sql, String[] entradas, String mensaje) { //Al hacer todo por swingquizas podamos eliminar String mensaje
        try {
            comprobarConexion();

            try ( PreparedStatement pst = con.prepareStatement(sql)) {

                if (entradas != null) {
                    for (int i = 0; i < entradas.length; i++) {
                        pst.setString(i + 1, entradas[i]);
                    }
                }
                
                int filasAfectadas = pst.executeUpdate(); //Se usa executeUpdate en vez de executeQuery, porque asi sabemos las lineas afectadas
                System.out.println(mensaje + ": " + filasAfectadas);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    /**
     * Ejecuta una consulta SELECT y devuelve un DefaultTableModel listo para
     * asignar a cualquier JTable de Swing.
     *
     * Las columnas se extraen automáticamente de los metadatos del ResultSet,
     * por lo que este método funciona con CUALQUIER consulta sin hardcodear
     * nombres de columna.
     *
     * Las celdas son de sólo lectura (isCellEditable devuelve false).
     *
     * Patrón de uso en un JFrame:
     *
     *   DefaultTableModel modelo = GestionBaseDeDatos.obtenerTableModel(
     *       "SELECT * FROM alumno ORDER BY nombre ASC", new String[0]
     *   );
     *   jTable1.setModel(modelo);
     *
     * @param sql    Consulta SQL con marcadores ? para los parámetros.
     * @param params Array de parámetros para los marcadores ? (puede ser new String[0]).
     * @return DefaultTableModel con cabeceras y filas; vacío si hay error.
     */
    public static DefaultTableModel obtenerTableModel(String sql, String[] params) {
 
        // Modelo de tabla de sólo lectura (isCellEditable = false)
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
 
        try {
            comprobarConexion();
 
            try (PreparedStatement pst = con.prepareStatement(sql)) {
 
                // Pasar parámetros al PreparedStatement
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        pst.setString(i + 1, params[i]);
                    }
                }
 
                try (ResultSet rs = pst.executeQuery()) {
 
                    ResultSetMetaData meta = rs.getMetaData();
                    int numColumnas = meta.getColumnCount();
 
                    // --- 1. Añadir cabeceras de columna (nombre de cada columna de la BD) ---
                    for (int i = 1; i <= numColumnas; i++) {
                        modelo.addColumn(meta.getColumnLabel(i).toUpperCase());
                    }
 
                    // --- 2. Añadir filas (cada fila del ResultSet es un Object[]) ---
                    while (rs.next()) {
                        Object[] fila = new Object[numColumnas];
                        for (int i = 0; i < numColumnas; i++) {
                            fila[i] = rs.getObject(i + 1);
                        }
                        modelo.addRow(fila);
                    }
                }
            }
 
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        return modelo;
    }
    
    /**
     * Comprueba si existe un registro con el código dado en la tabla indicada.
     * Se usa para validar claves foráneas ANTES de insertar o actualizar.
     *
     * Ejemplos de uso en formularios Swing:
     *   if (!GestionBaseDeDatos.existeRegistro("ciclo", codigoCiclo)) {
     *       JOptionPane.showMessageDialog(this, "El ciclo no existe.");
     *       return;
     *   }
     *
     * @param tabla  Nombre de la tabla (p. ej. "ciclo", "alumno", "modulo").
     * @param codigo Código a buscar (clave primaria entera).
     * @return true si existe al menos un registro con ese código; false en caso contrario.
     */
    public static boolean existeRegistro(String tabla, int codigo) {
        // Se construye la SQL con el nombre de tabla 
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE codigo = ?";
        try {
            comprobarConexion(); 
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, codigo);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
