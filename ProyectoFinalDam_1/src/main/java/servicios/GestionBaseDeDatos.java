/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import Config.appConfig;
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
                con = DriverManager.getConnection(appConfig.urlSQL, appConfig.nombreUsuarioSQL, appConfig.contraseñaSQL);
                System.out.println("Conexion exitosa");

            } catch (SQLException e) {
                System.out.println("Fallo en la conexion, error: " + e.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al vincular la base de datos, introduzca los parametros correctos en SQL WORKBENCH");
        }

    }

    /**
     * Lee todas las matriculas y las crea en el programa
     */
    public static void leerMatriculaBDD() {
        try {
            PreparedStatement pstm = con.prepareCall("Select * from matricula");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                int codigoAlumno = rs.getInt("codigo_alumno");
                int anyoAcademico = rs.getInt("año_academico");
                String estado = rs.getString("estado");
                double importe = rs.getDouble("importe");

                Matricula matricula = new Matricula(codigo, codigoAlumno, anyoAcademico, estado, importe);
            }

        } catch (SQLException e) {
            System.out.println("Error en la lectura, error: " + e);
        }
    }

    /**
     * Lee todos los modulos y los crea en el programa
     */
    public static void leerModulosBDD() {
        try {
            PreparedStatement pstm = con.prepareCall("Select * from modulo");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                int codigoCiclo = rs.getInt("codigo_ciclo");
                String nombre = rs.getString("nombre");
                String curso = rs.getString("curso");
                int creditosEtcs = rs.getInt("creditos_ects");
                int horas = rs.getInt("horas");

                Modulo Modulo = new Modulo(codigo, codigoCiclo, nombre, curso, creditosEtcs, horas);
            }

        } catch (SQLException e) {
            System.out.println("Error en la lectura, error: " + e);
        }
    }

    /**
     * Lee todos los ciclos y los crea en el programa
     */
    public static void leerCiclosBDD() {

    }

    /**
     * Lee todos los alumnos y los crea en el programa
     */
    public static void leerAlumnosBDD() {

    }

    /**
     * Lee todas las lineas de la lineaMatricula y las crea en el programa
     */
    public static void leerLineaMatriculaBDD() {

    }

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

    public static void insertarBDD(Object obj) {
        vincularBDD();
        try {
            if (obj instanceof Alumno alumno) {
                String query = inserciones.insertarAlumno(alumno);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof Matricula matricula) {
                String query = inserciones.insertarMatricula(matricula);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof LineaMatricula lineaMatricula) {
                String query = inserciones.insertarLineaMatricula(lineaMatricula);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof Ciclo ciclo) {
                String query = inserciones.insertarCiclo(ciclo);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof Modulo modulo) {
                String query = inserciones.insertarModulo(modulo);
                PreparedStatement ps = con.prepareStatement(query);
            }
        } catch(SQLException e)  {

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
}
