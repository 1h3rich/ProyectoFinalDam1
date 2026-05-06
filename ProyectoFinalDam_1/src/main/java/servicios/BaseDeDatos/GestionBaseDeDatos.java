/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.BaseDeDatos;

import Config.AppConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isard
 */
public class GestionBaseDeDatos {

    private static Connection con;

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
                con = DriverManager.getConnection(AppConfig.urlSQL, AppConfig.nombreUsuarioSQL, AppConfig.contraseñaSQL[0]);
                System.out.println("Conexion exitosa");

            } catch (SQLException e) {
                try {
                    con = DriverManager.getConnection(AppConfig.urlSQL, AppConfig.nombreUsuarioSQL, AppConfig.contraseñaSQL[1]);
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
     * pasandole un array con la consulta y las columnas que tiene, un boolean
     * que indica si necesita parametros de entrada la consulta y los parametros
     * que necesita con un maximo de 3
     *
     * @param datosConsulta
     * @param entradaBoolean
     * @param entrada1
     * @param entrada2
     * @param entrada3
     */
    public static void realizarConsulta(String[] datosConsulta, boolean entradaBoolean, String entrada1, String entrada2, String entrada3) {//Todavia falta poner más visual la tabla
        try {
            PreparedStatement pst = con.prepareStatement(datosConsulta[0]);

            if (entradaBoolean) {
                pst.setString(1, entrada1);
                if (entrada2 != null) {
                    pst.setString(2, entrada2);
                }
                if (entrada3 != null) {
                    pst.setString(3, entrada3);
                }

            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) { //Salta a la siguiente línea de la tabla
                for (int i = 1; i < datosConsulta.length; i++) {
                    System.out.print(rs.getString(datosConsulta[i]) + " ");
                }
                System.out.println("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
