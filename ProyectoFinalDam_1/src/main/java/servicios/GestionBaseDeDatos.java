/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import Utils.Configuracion;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Matricula;

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
                System.out.println("Fallo en la conexion, error: " + e.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al vincular la base de datos, introduzca los parametros correctos en SQL WORKBENCH");
        }

    }

    public static void leerMatriculaBDD() {
        try{
            String consulta = "Select * from matricula";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            
            while(rs.next()){
                int codigo = rs.getInt("codigo");
                int codigoAlumno = rs.getInt("codigo_alumno");
                int anyoAcademico = rs.getInt("año_academico");
                String estado = rs.getString("estado");
                double importe = rs.getDouble("importe");
                
                Matricula matricula = new Matricula(codigo, codigoAlumno, anyoAcademico, estado, importe);
                
                System.out.println(matricula.getCodigo());
            }
            
        }catch(SQLException e){
            
        }
    }

    public static void leerModulosBDD() {

    }

    public static void leerCiclosBDD() {

    }

    public static void leerAlumnosBDD() {

    }

    public static void leerLineaMatriculaBDD() {
        
    }
    
    public static int leerCodigoBDD(String string){
        String temp = "";
        int codigo = -1;
        if(string.equalsIgnoreCase("alumno")) temp = "alumno";
        if(string.equalsIgnoreCase("ciclo")) temp = "ciclo";
        if(string.equalsIgnoreCase("lineamatricula")) temp = "linea_matricula";
        if(string.equalsIgnoreCase("matricula")) temp = "matricula";
        if(string.equalsIgnoreCase("modulo")) temp = "modulo";

        try{
            //String consulta = "Select count(codigo) from" + temp;
            PreparedStatement stmt = con.prepareStatement("Select count(codigo) from ?");
            stmt.setString(1, temp);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                codigo = rs.getInt("codigo");
            }
            
        }catch(SQLException e){
            System.out.println("Ha ocurrido un error: " + e);
        }
        return codigo++;
    }
    
    public static void cerrarBDD(){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestionBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
