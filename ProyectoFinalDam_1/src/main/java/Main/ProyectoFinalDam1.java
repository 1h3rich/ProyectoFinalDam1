package Main;

import servicios.BaseDeDatos.GestionBaseDeDatos;
import modelos.*;
import java.time.LocalDate;
import java.util.Scanner;
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
        
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Proyecto Final");
        System.out.println("");
        
        // Pruebas Base de datos
        GestionBaseDeDatos.vincularBDD();
        /*
        System.out.println("Consulta 1:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta1, false, null, null , null);
        System.out.println("");
       /* 
        System.out.println("Introduce un nivel de ciclo");
        String nivelCiclo = teclado.nextLine();
        System.out.println("Introduce un curso de modulo");
        String cursoDeModulo = teclado.nextLine();
        
        
        System.out.println("Consulta 2:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta2,true, nivelCiclo, cursoDeModulo, null);
        System.out.println("");
        */
        
        System.out.println("Introduce el año academico");
        String añoAcademico = teclado.next();
        
        
        System.out.println("Consulta 3:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta3,true,añoAcademico ,null,null);
        System.out.println("");
        
        
        
        
        
        /*
        System.out.println("Consulta 4:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta4);
        System.out.println("");
        
        System.out.println("Consulta 5:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta5);
        System.out.println("");
        
        System.out.println("Consulta 6:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta6);
        System.out.println("");
        
        System.out.println("Consulta 7:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta7);
        System.out.println("");
        
        System.out.println("Consulta 5:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta5);
        System.out.println("");
        
        
        System.out.println("Consulta 9:");
        GestionBaseDeDatos.realizarConsulta(ConsultasMultiTabla.datosConsulta1);
        System.out.println("");
        
        */
        
        //Pruebas modelos
        
        //Alumno alumno_1 = new Alumno(nombre, LocalDate.MIN, domicilio, 0, correo);
        //Ciclo cilo_1 = new Ciclo(0, nombre, denominacion, familiaProfesional, 0, 0);
        //LineaMatricula lineaMatricula_1 = new LineaMatricula(0, 0, 0);
        //Matricula matricula_1 = new Matricula(0, estado, 0);
        //Modulo modulo_1 = new Modulo(nombre, curso, 0, 0);
    }
    
}
