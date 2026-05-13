package servicios.BaseDeDatos;

import Config.Config;
import Control.SesionDatos;
import Utils.ItemCombo;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelos.*;

public class GestionBaseDeDatos {

    private static Connection con;
    private static boolean transaccionActiva = false;

    public static TreeSet<Alumno> listaAlumno = new TreeSet<>();
    public static TreeSet<Matricula> listaMatricula = new TreeSet<>();
    public static TreeSet<LineaMatricula> listaLineaMatricula = new TreeSet<>();
    public static TreeSet<Ciclo> listaCiclo = new TreeSet<>();
    public static TreeSet<Modulo> listaModulo = new TreeSet<>();

    //En esta lista hay que guaradr los datos que insertan cada vez que se hace un insert
    public static ArrayList<String> datosInsertados = new ArrayList<>(); //Aqui no estoy seguro si es String o podria Ser de Tipo Object, si se puede elegir pondria Object

    /**
     * Conecta Java con la base de datos MySQL.
     *
     * @return
     */
    public static boolean vincularBDD() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");

            con = DriverManager.getConnection(
                    Config.urlSQL,
                    Config.nombreUsuarioSQL,
                    Config.contraseñaSQL[0]
            );

            con.setAutoCommit(true);

            System.out.println("Conexion exitosa");
            System.out.println("URL Java: " + Config.urlSQL);
            System.out.println("Usuario Java: " + Config.nombreUsuarioSQL);

            diagnosticoConexion();

            return true;

        } catch (ClassNotFoundException | SQLException e) {

            try {
                con = DriverManager.getConnection(
                        Config.urlSQL,
                        Config.nombreUsuarioSQL,
                        Config.contraseñaSQL[1]
                );

                System.out.println("Conexion exitosa");
                System.out.println("URL Java: " + Config.urlSQL);
                System.out.println("Usuario Java: " + Config.nombreUsuarioSQL);
                diagnosticoConexion();
                return true;

            } catch (SQLException ex) {
                Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }
    }

    public static void diagnosticoConexion() {
        String sql = """
        SELECT 
            DATABASE() AS base_actual,
            @@hostname AS host,
            @@port AS puerto,
            @@autocommit AS autocommit,
            (SELECT COUNT(*) FROM alumno) AS total_alumnos
        """;

        try ( PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                System.out.println("Base Java: " + rs.getString("base_actual"));
                System.out.println("Host Java: " + rs.getString("host"));
                System.out.println("Puerto Java: " + rs.getString("puerto"));
                System.out.println("Autocommit Java: " + rs.getString("autocommit"));
                System.out.println("Alumnos Java: " + rs.getInt("total_alumnos"));
            }

        } catch (SQLException e) {
            System.out.println("Error en diagnosticoConexion:");
            e.printStackTrace();
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
    private static boolean comprobarConexion() {
        try {
            if (con == null || con.isClosed()) {
                return vincularBDD();
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error al comprobar conexión: " + e.getMessage());
            return false;
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
            SesionDatos.registrarAlumno(alumno);

        } else if (tipoObjeto.equalsIgnoreCase("Ciclo")) {
            Ciclo ciclo = new Ciclo(datos);
            SesionDatos.registrarCiclo(ciclo);

        } else if (tipoObjeto.equalsIgnoreCase("LineaMatricula")) {
            LineaMatricula lineaMatricula = new LineaMatricula(datos);
            SesionDatos.registrarLineaMatricula(lineaMatricula);

        } else if (tipoObjeto.equalsIgnoreCase("Matricula")) {
            Matricula matricula = new Matricula(datos);
            SesionDatos.registrarMatricula(matricula);

        } else if (tipoObjeto.equalsIgnoreCase("Modulo")) {
            Modulo modulo = new Modulo(datos);
            SesionDatos.registrarModulo(modulo);

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
     * DefaultTableModel modelo = GestionBaseDeDatos.obtenerTableModel( "SELECT
     * * FROM alumno ORDER BY nombre ASC", new String[0] );
     * jTable1.setModel(modelo);
     *
     * @param sql Consulta SQL con marcadores ? para los parámetros.
     * @param params Array de parámetros para los marcadores ? (puede ser new
     * String[0]).
     * @return DefaultTableModel con cabeceras y filas; vacío si hay error.
     */
    public static DefaultTableModel obtenerTableModel(String sql, String[] params) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (!comprobarConexion()) {
            System.out.println("No hay conexión con la base de datos.");
            return modelo;
        }

        System.out.println("SQL ejecutada:");
        System.out.println(sql);

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                System.out.println("Parametro " + (i + 1) + ": " + params[i]);
            }
        }

        try ( PreparedStatement pst = con.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setString(i + 1, params[i]);
                }
            }

            try ( ResultSet rs = pst.executeQuery()) {

                ResultSetMetaData meta = rs.getMetaData();
                int numColumnas = meta.getColumnCount();

                for (int i = 1; i <= numColumnas; i++) {
                    modelo.addColumn(meta.getColumnLabel(i));
                }

                while (rs.next()) {
                    Object[] fila = new Object[numColumnas];

                    for (int i = 0; i < numColumnas; i++) {
                        fila[i] = rs.getObject(i + 1);
                    }

                    modelo.addRow(fila);
                    System.out.println("SQL ejecutada:");
                    System.out.println(sql);
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CARGAR TABLA:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return modelo;
    }

    /**
     * Comprueba si existe un registro con el código dado en la tabla indicada.
     * Se usa para validar claves foráneas ANTES de insertar o actualizar.
     *
     * Ejemplos de uso en formularios Swing: if
     * (!GestionBaseDeDatos.existeRegistro("ciclo", codigoCiclo)) {
     * JOptionPane.showMessageDialog(this, "El ciclo no existe."); return; }
     *
     * @param tabla Nombre de la tabla (p. ej. "ciclo", "alumno", "modulo").
     * @param codigo Código a buscar (clave primaria entera).
     * @return true si existe al menos un registro con ese código; false en caso
     * contrario.
     */
    public static boolean existeRegistro(String tabla, int codigo) {
        // Se construye la SQL con el nombre de tabla 
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE codigo = ?";
        try {
            comprobarConexion();
            try ( PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, codigo);
                try ( ResultSet rs = pst.executeQuery()) {
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

    public static int obtenerUltimoCodigo(String tabla) {
        String sql = "SELECT MAX(codigo) FROM " + tabla;

        try {
            comprobarConexion();

            try ( PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public static int insertarAlumnoYDevolverID(String[] entradas) {
        comprobarConexion();

        String sql = ConsultasSQL.INSERT_ALUMNO[0];

        System.out.println("SQL alumno: " + sql);
        System.out.println("Nombre: " + entradas[0]);
        System.out.println("Correo: " + entradas[1]);
        System.out.println("Domicilio: " + entradas[2]);
        System.out.println("Teléfono: " + entradas[3]);
        System.out.println("Fecha: " + entradas[4]);

        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entradas[0]);
            pst.setString(2, entradas[1]);
            pst.setString(3, entradas[2]);
            pst.setString(4, entradas[3]);
            pst.setDate(5, java.sql.Date.valueOf(entradas[4]));

            int filas = pst.executeUpdate();

            if (filas > 0) {
                try ( ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println("ERROR SQL AL INSERTAR ALUMNO:");
            System.out.println(ex.getMessage());
            ex.printStackTrace();

        } catch (IllegalArgumentException ex) {
            System.out.println("ERROR EN FORMATO DE FECHA:");
            System.out.println("La fecha debe ser YYYY-MM-DD. Ejemplo: 2003-05-12");
            ex.printStackTrace();
        }

        return -1;
    }

    public static int insertarMatriculaYDevolverID(String[] datos) {
        comprobarConexion();

        String sql = ConsultasSQL.INSERT_MATRICULA[0];

        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, datos[0]);
            pst.setDouble(2, Double.parseDouble(datos[1]));
            pst.setString(3, datos[2]);
            pst.setInt(4, Integer.parseInt(datos[3]));

            int filas = pst.executeUpdate();

            if (filas > 0) {
                try ( ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar matrícula: " + e.getMessage());
        }

        return -1;
    }

    public static boolean insertarLineaMatricula(String[] datos) {
        comprobarConexion();

        String sql = ConsultasSQL.INSERT_LINEA_MATRICULA[0];

        try ( PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, Integer.parseInt(datos[0]));
            pst.setInt(2, Integer.parseInt(datos[1]));
            pst.setInt(3, Integer.parseInt(datos[2]));
            pst.setDouble(4, Double.parseDouble(datos[3]));
            pst.setDouble(5, Double.parseDouble(datos[4]));

            int filas = pst.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar línea de matrícula: " + e.getMessage());
            return false;
        }
    }

    public static int insertarCicloYDevolverID(String[] datos) {
        comprobarConexion();

        String sql = ConsultasSQL.INSERT_CICLO[0];

        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, datos[0]);
            pst.setString(2, datos[1]);
            pst.setString(3, datos[2]);
            pst.setInt(4, Integer.parseInt(datos[3]));
            pst.setInt(5, Integer.parseInt(datos[4]));

            int filas = pst.executeUpdate();

            if (filas > 0) {
                try ( ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar ciclo: " + e.getMessage());
        }

        return -1;
    }

    public static int insertarModuloYDevolverID(String[] datos) {
        comprobarConexion();

        String sql = ConsultasSQL.INSERT_MODULO[0];

        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, datos[0]);
            pst.setString(2, datos[1]);
            pst.setInt(3, Integer.parseInt(datos[2]));
            pst.setInt(4, Integer.parseInt(datos[3]));
            pst.setInt(5, Integer.parseInt(datos[4]));

            int filas = pst.executeUpdate();

            if (filas > 0) {
                try ( ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar módulo: " + e.getMessage());
        }

        return -1;
    }

    public static void iniciarTransaccion() {
        try {
            comprobarConexion();

            if (con != null && !con.isClosed()) {
                con.setAutoCommit(false);
                transaccionActiva = true;
                System.out.println("Transacción iniciada");
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar transacción: " + e.getMessage());
        }
    }

    public static void confirmarTransaccion() {
        try {
            if (con != null && transaccionActiva) {
                con.commit();
                con.setAutoCommit(true);
                transaccionActiva = false;
                System.out.println("Transacción confirmada");
            }

        } catch (SQLException e) {
            System.out.println("Error al confirmar transacción: " + e.getMessage());
        }
    }

    public static void cancelarTransaccion() {
        try {
            if (con != null && transaccionActiva) {
                con.rollback();
                con.setAutoCommit(true);
                transaccionActiva = false;
                System.out.println("Transacción cancelada");
            }

        } catch (SQLException e) {
            System.out.println("Error al cancelar transacción: " + e.getMessage());
        }
    }

    public static void cargarDenominacionesCiclosEnComboBox(javax.swing.JComboBox<String> comboBox) {
        comprobarConexion();

        String sql = "SELECT denominacion FROM ciclo ORDER BY denominacion ASC";

        comboBox.removeAllItems();

        try ( PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                comboBox.addItem(rs.getString("denominacion"));
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar ciclos en ComboBox: " + e.getMessage());
        }
    }

    public static ArrayList<Utils.ItemCombo> obtenerCiclosCombo() {
        comprobarConexion();

        ArrayList<Utils.ItemCombo> lista = new ArrayList<>();

        String sql = "SELECT codigo, denominacion FROM ciclo ORDER BY denominacion ASC";

        try ( PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("codigo");
                String denominacion = rs.getString("denominacion");

                lista.add(new Utils.ItemCombo(id, denominacion));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener ciclos: " + e.getMessage());
        }

        return lista;
    }

    public static ArrayList<Utils.ItemCombo> obtenerModulosPorCicloCombo(int idCiclo) {
        comprobarConexion();

        ArrayList<Utils.ItemCombo> lista = new ArrayList<>();

        String sql = "SELECT codigo, nombre FROM modulo WHERE codigo_ciclo = ? ORDER BY nombre ASC";

        try ( PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idCiclo);

            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("codigo");
                    String nombre = rs.getString("nombre");

                    lista.add(new Utils.ItemCombo(id, nombre));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener módulos del ciclo: " + e.getMessage());
        }

        return lista;
    }

    public static int contarModulosPorCiclo(int idCiclo) {
        comprobarConexion();

        String sql = "SELECT COUNT(*) FROM modulo WHERE codigo_ciclo = ?";

        try ( PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idCiclo);

            try ( ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al contar módulos del ciclo: " + e.getMessage());
        }

        return 0;
    }

    public static ArrayList<ItemCombo> obtenerModulosDisponiblesCombo() {
        comprobarConexion();

        ArrayList<ItemCombo> lista = new ArrayList<>();

        String sql = "SELECT codigo, nombre FROM modulo WHERE codigo_ciclo ORDER BY nombre ASC";

        try ( PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                lista.add(new ItemCombo(id, nombre));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener módulos disponibles: " + e.getMessage());
        }

        return lista;
    }

    public static boolean asignarModuloACiclo(int idModulo, int idCiclo) {
        comprobarConexion();

        String sql = "UPDATE modulo SET codigo_ciclo = ? WHERE codigo = ?";

        try ( PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idCiclo);
            pst.setInt(2, idModulo);

            int filas = pst.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al asignar módulo al ciclo: " + e.getMessage());
        }

        return false;
    }

    public static void comprobarBaseActual() {
        if (!comprobarConexion()) {
            System.out.println("No hay conexión.");
            return;
        }

        String sql = """
                 SELECT 
                    DATABASE() AS base_actual,
                    (SELECT COUNT(*) FROM alumno) AS total_alumnos,
                    (SELECT COUNT(*) FROM modulo) AS total_modulos,
                    (SELECT COUNT(*) FROM ciclo) AS total_ciclos
                 """;

        try ( PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                System.out.println("Base usada por Java: " + rs.getString("base_actual"));
                System.out.println("Alumnos vistos por Java: " + rs.getInt("total_alumnos"));
                System.out.println("Módulos vistos por Java: " + rs.getInt("total_modulos"));
                System.out.println("Ciclos vistos por Java: " + rs.getInt("total_ciclos"));
            }

        } catch (SQLException e) {
            System.out.println("Error comprobando base actual: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta cualquier INSERT con AUTO_INCREMENT y devuelve el ID generado.
     *
     * Este método reemplaza los 4 métodos específicos que existían antes
     * (insertarAlumnoYDevolverID, insertarCicloYDevolverID, etc.), ya que todos
     * eran copias idénticas que solo diferían en el SQL y los params. Ahora
     * basta con pasar la SQL de ConsultasSQL y el array de valores.
     *
     * MySQL convierte automáticamente los String al tipo de columna
     * correcto (igual que ya hace insertarDatos con pst.setString).
     *
     * Ejemplo de uso desde CrearAlumno: int id =
     * GestionBaseDeDatos.insertarYDevolverID( ConsultasSQL.INSERT_ALUMNO[0],
     * new String[]{ nombre, correo, domicilio, telefono, fecha } ); if (id !=
     * -1) { SesionDatos.registrarAlumno(new Alumno(...)); }
     *
     * @param sql Sentencia INSERT con marcadores ?
     * (ConsultasSQL.INSERT_XXX[0]).
     * @param params Valores para los marcadores, en el mismo orden que la SQL.
     * @return ID generado por AUTO_INCREMENT, o -1 si falla.
     */
    public static int insertarYDevolverID(String sql, String[] params) {
        comprobarConexion();
        try ( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }
            int filas = pst.executeUpdate();
            if (filas > 0) {
                try ( ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en insertarYDevolverID: " + ex.getMessage());
        }
        return -1;
    }

    /**
     * Versión de insertarYDevolverID para tablas con clave primaria COMPUESTA
     * que no usan AUTO_INCREMENT (linea_matricula).
     *
     * Ejemplo: boolean ok = GestionBaseDeDatos.insertarSinID(
     * ConsultasSQL.INSERT_LINEA_MATRICULA[0], new String[]{ codMatricula,
     * codModulo, repeticion, cal1, cal2 } );
     *
     * @param sql Sentencia INSERT.
     * @param params Valores para los marcadores ?.
     * @return true si se insertó al menos una fila.
     */
    public static boolean insertarSinID(String sql, String[] params) {
        comprobarConexion();
        try ( PreparedStatement pst = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pst.setString(i + 1, params[i]);
            }
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en insertarSinID: " + ex.getMessage());
        }
        return false;
    }

    /**
     * @deprecated Usa insertarYDevolverID(ConsultasSQL.INSERT_ALUMNO[0],
     * params)
     */
    /*
    public static int insertarAlumnoYDevolverID(String[] entradas) {
        return insertarYDevolverID(ConsultasSQL.INSERT_ALUMNO[0], entradas);
    }
     */
    /**
     * @deprecated Usa insertarYDevolverID(ConsultasSQL.INSERT_MATRICULA[0],
     * datos)
     */
    /*
    public static int insertarMatriculaYDevolverID(String[] datos) {
        return insertarYDevolverID(ConsultasSQL.INSERT_MATRICULA[0], datos);
    }
     */
    /**
     * @deprecated Usa insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[0],
     * datos)
     */
    /*
    public static boolean insertarLineaMatricula(String[] datos) {
        return insertarSinID(ConsultasSQL.INSERT_LINEA_MATRICULA[0], datos);
    }
     */
    /**
     * @deprecated Usa insertarYDevolverID(ConsultasSQL.INSERT_CICLO[0], datos)
     */
    /*
    public static int insertarCicloYDevolverID(String[] datos) {
        return insertarYDevolverID(ConsultasSQL.INSERT_CICLO[0], datos);
    }
     */
    /**
     * @deprecated Usa insertarYDevolverID(ConsultasSQL.INSERT_MODULO[0], datos)
     */
    /*
    public static int insertarModuloYDevolverID(String[] datos) {
        return insertarYDevolverID(ConsultasSQL.INSERT_MODULO[0], datos);
    }
     */
}
