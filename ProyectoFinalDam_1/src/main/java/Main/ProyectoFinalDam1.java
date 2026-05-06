package Main;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import modelos.*;
import java.time.LocalDate;
import servicios.BaseDeDatos.ConsultasMultiTabla;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author isard
 */
public class ProyectoFinalDam1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Proyecto Final");
        System.out.println("");
        
        // Pruebas Base de datos
        GestionBaseDeDatos.vincularBDD();
         
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.consulta1);
        
        
        //Pruebas modelos
        
        //Alumno alumno_1 = new Alumno(nombre, LocalDate.MIN, domicilio, 0, correo);
        //Ciclo cilo_1 = new Ciclo(0, nombre, denominacion, familiaProfesional, 0, 0);
        //LineaMatricula lineaMatricula_1 = new LineaMatricula(0, 0, 0);
        //Matricula matricula_1 = new Matricula(0, estado, 0);
        //Modulo modulo_1 = new Modulo(nombre, curso, 0, 0);
    }
    
}
