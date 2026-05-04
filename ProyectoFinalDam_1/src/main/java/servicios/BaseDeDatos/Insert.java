/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.BaseDeDatos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelos.*;
import static servicios.BaseDeDatos.GestionBaseDeDatos.vincularBDD;
import java.sql.Connection;
/**
 *
 * @author Rich
 */
public class Insert {
    
    private static Connection con;
    
    /**
     * No usar, llamar a insertarBDD
     * @param alumno
     * @return 
     */
    public static String insertarAlumno(Alumno alumno){
        return "insert into alumno (nombre, fecha_nacimiento, domicilio, telefono, correo ) values (?, ?, ?, ?, ?)";
    }
    /**
     * No usar, llamar a insertarBDD
     * @param matricula
     * @return 
     */
    public static String insertarMatricula(Matricula matricula){
        return "insert into matricula (codigo_alumno, año_academico, estado, importe) values (?, ?, ?, ?)";
    }
    
    /**
     * No usar, llamar a insertarBDD
     * @param lineaMatricula
     * @return 
     */
    public static String insertarLineaMatricula(LineaMatricula lineaMatricula){
        return "insert into linea_matricula (codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda) values (?, ?, ?, ?, ?)";
    }
    
    /**
     * No usar, llamar a insertarBDD
     * @param ciclo
     * @return 
     */
    public static String insertarCiclo(Ciclo ciclo){
        return "insert into ciclo (denominacion, familia_profresional, nivel, horas, año_curriculum ) values (?, ?, ?, ?, ?)";
    }
    
    /**
     * No usar, llamar a insertarBDD
     * @param modulo
     * @return 
     */
    public static String insertarModulo(Modulo modulo){
        return "insert into modulo (codigo_ciclo, nombre, curso, creditos_etcs, horas ) values (?, ?, ?, ?, ?)";
    }
    
    /**
     * Inserta el objeto enviado dependiendo del tipo de objeto recibido.
     * @param obj 
     */
     public static void insertarBDD(Object obj) {
        vincularBDD();
        try {
            if (obj instanceof Alumno alumno) {
                String query = Insert.insertarAlumno(alumno);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof Matricula matricula) {
                String query = Insert.insertarMatricula(matricula);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof LineaMatricula lineaMatricula) {
                String query = Insert.insertarLineaMatricula(lineaMatricula);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof Ciclo ciclo) {
                String query = Insert.insertarCiclo(ciclo);
                PreparedStatement ps = con.prepareStatement(query);
            }
            if (obj instanceof Modulo modulo) {
                String query = Insert.insertarModulo(modulo);
                PreparedStatement ps = con.prepareStatement(query);
            }
        } catch(SQLException e)  {

        }
    }
}
