/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 1DAM
 */
public class ConsultasEspecificas {
    
    private static Connection con;

    /**
     * Lee el codigo del parametro dado.
     *
     * @param string
     * @return
     */
    public static int leerCodigoBDD(String string) {
        String temp = "";
        int codigo = -1;
        if (string.equalsIgnoreCase("alumno")) {
            temp = "alumno";
        }
        if (string.equalsIgnoreCase("ciclo")) {
            temp = "ciclo";
        }
        if (string.equalsIgnoreCase("lineamatricula")) {
            temp = "linea_matricula";
        }
        if (string.equalsIgnoreCase("matricula")) {
            temp = "matricula";
        }
        if (string.equalsIgnoreCase("modulo")) {
            temp = "modulo";
        }
        try {
            PreparedStatement stmt = con.prepareStatement("Select count(codigo) from ?");
            stmt.setString(1, temp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error: " + e);
        }
        return codigo++;
    }
    
}
