/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import Utils.Configuracion;
import java.sql.*;

/**
 *
 * @author isard
 */
public class GestionBaseDeDatos {

    private static Connection con;
    /**
     * Aunque no es necesario, se comprubea el driver e informa, además despues prueba la conexion, confirma y entonces se queda abierta
     * hasta que un metodo posterior la cierre.
     * 
     * Si algo falla, salta el try catch.
     */
    public static void vincularBDD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Base de datos vinculada correctamente");

            System.out.println("Probando conexion...");
            try {
                con = DriverManager.getConnection(Configuracion.urlSQL, Configuracion.nombreUsuarioSQL, Configuracion.contraseñaSQL);
                System.out.println("Conexion exitosa");
                
            } catch (SQLException e) {
                System.out.println("Fallo en la conexion, error:" + e.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al vincular la base de datos, introduzca los parametros correctos en SQL WORKBENCH");
        }

    }

    public static void leerMatriculaBDD() {

    }

    public static void leerModulosBDD() {

    }

    public static void leerCiclosBDD() {

    }

    public static void leerAlumnosBDD() {

    }

    public static void leerLineaMatriculaBDD() {

    }
}
