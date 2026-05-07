/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.BaseDeDatos;

import Config.Config;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.*;

/**
 *
 * @author isard
 */
public class GestionBaseDeDatos {

    private static Connection con;

    private static ArrayList<Alumno> listaAlumnos = new ArrayList<>();
    private static ArrayList<Ciclo> listaCiclos = new ArrayList<>();
    private static ArrayList<LineaMatricula> listaLineasMatricula = new ArrayList<>();
    private static ArrayList<Matricula> listaMatriculas = new ArrayList<>();
    private static ArrayList<Modulo> listaModulos = new ArrayList<>();

    /**
     * Aunque no es necesario, se comprubea el driver e informa, además despues
     * prueba la conexion, confirma y entonces se queda abierta hasta que un
     * metodo posterior la cierre.
     *
     * Si algo falla, salta el try catch.
     */
    public static void vincularBDD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Base de datos vinculada correctamente");

            System.out.println("Probando conexion...");
            try {
                con = DriverManager.getConnection(Config.urlSQL, Config.nombreUsuarioSQL, Config.contraseñaSQL[0]);
                System.out.println("Conexion exitosa");

            } catch (SQLException e) {
                try {
                    con = DriverManager.getConnection(Config.urlSQL, Config.nombreUsuarioSQL, Config.contraseñaSQL[1]);
                    System.out.println("Conexion exitosa");
                } catch (SQLException ex) {
                    System.out.println("Error en la contraseña del WorkBench");
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al vincular la base de datos, introduzca los parametros correctos en SQL WORKBENCH");
        }

    }

    /**
     * Cierra la conexion
     */
    public static void cerrarBDD() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo con el cual ejecutas por consola la consulta que quieras,
     * pasandole un array con la consulta y las columnas que tiene, ademas de un
     * array con las entradas
     *
     * @param datosConsulta
     * @param entradas
     * @param mostrarPorPantalla
     *
     */
    public static void realizarSQL(String[] datosConsulta, String entradas[], boolean mostrarPorPantalla, boolean guardarDatos) {//Todavia falta poner más visual la tabla
        try {
            PreparedStatement pst = con.prepareStatement(datosConsulta[0]);

            for (int i = 0; i < entradas.length; i++) {
                pst.setString(i + 1, entradas[i]);
            }

            ResultSet rs = pst.executeQuery();

            if (mostrarPorPantalla) {
                while (rs.next()) { //Salta a la siguiente línea de la tabla
                    for (int i = 1; i < datosConsulta.length; i++) {
                        System.out.print(rs.getString(datosConsulta[i]) + " ");
                    }
                    System.out.println("");
                }
            }
            if(guardarDatos){
                String objeto [] = new String [10];
                
                while (rs.next()) { //Salta a la siguiente línea de la tabla
                    for (int i = 1; i < datosConsulta.length; i++) {
                        rs.getString(datosConsulta[i]);
                    }
                    System.out.println("");
                }
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertarDatos(String[] datosInsertar, String entradas[]) {
        try {
            PreparedStatement pst = con.prepareStatement(datosInsertar[0]);

            for (int i = 0; i < entradas.length; i++) {
                pst.setString(i + 1, entradas[i]);
            }
            ResultSet rs = pst.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    public static void consultarTabla(String[] datosTablas, boolean ordenar, String datoOrdenacion) {
        try {
            PreparedStatement pst = con.prepareStatement(datosTablas[0]);

            if (ordenar) {
                pst.setString(1, datoOrdenacion);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) { //Salta a la siguiente línea de la tabla
                for (int i = 1; i < datosTablas.length; i++) {
                    System.out.print(rs.getString(datosTablas[i]) + " ");
                }
                System.out.println("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     */
    public static void actualizarFila(String[] datosActualizacion, String entradas[]) {
        try {
            PreparedStatement pst = con.prepareStatement(datosActualizacion[0]);

            for (int i = 0; i < entradas.length; i++) {
                pst.setString(i + 1, entradas[i]);
            }

            ResultSet rs = pst.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void eliminarFila(String[] datosEliminar, String entradas[]) {
        try {
            PreparedStatement pst = con.prepareStatement(datosEliminar[0]);

            for (int i = 0; i < entradas.length; i++) {
                pst.setString(i + 1, entradas[i]);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) { //Salta a la siguiente línea de la tabla
                for (int i = 1; i < datosEliminar.length; i++) {
                    System.out.print(rs.getString(datosEliminar[i]) + " ");
                }
                System.out.println("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
