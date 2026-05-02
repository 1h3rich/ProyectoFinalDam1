/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import modelos.*;

/**
 *
 * @author Rich
 */
public class inserciones {
    
    public static String insertarAlumno(Alumno alumno){
        return "insert into alumno (nombre, fecha_nacimiento, domicilio, telefono, correo ) values (?, ?, ?, ?, ?)";
    }
    public static String insertarMatricula(Matricula matricula){
        return "insert into matricula (codigo_alumno, año_academico, estado, importe) values (?, ?, ?, ?)";
    }
    public static String insertarLineaMatricula(LineaMatricula lineaMatricula){
        return "insert into linea_matricula (codigo_matricula, codigo_modulo, repeticion, calificacion_primera, calificacion_segunda) values (?, ?, ?, ?, ?)";
    }
    public static String insertarCiclo(Ciclo ciclo){
        return "insert into ciclo (denominacion, familia_profresional, nivel, horas, año_curriculum ) values (?, ?, ?, ?, ?)";
    }
    public static String insertarModulo(Modulo modulo){
        return "insert into modulo (codigo_ciclo, nombre, curso, creditos_etcs, horas ) values (?, ?, ?, ?, ?)";
    }
}
