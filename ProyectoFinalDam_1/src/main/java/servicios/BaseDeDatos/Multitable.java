/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.BaseDeDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.Matricula;
import modelos.Modulo;

/**
 *
 * @author 1DAM
 */
public class Multitable {
    
    private static Connection con;
    
    
    //===========LECTURA===============
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

    
}
