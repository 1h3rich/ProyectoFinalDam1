/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import interfaces.interpolaridadDeDatos;
import interfaces.persistenciaSQL;

/**
 *
 * @author isard
 */
public class Modulo implements persistenciaSQL{
    // Aqui va la creacion del objeto Modulo, el cual deberemos meter en la base de datos 
    private int codigo;
    private int codigo_ciclo;
    private Ciclo ciclo;
    private String nombre;
    private String curso;
    private int creditos_ects;
    private int horas;

    /**
     *
     * @param nombre
     * @param curso
     * @param creditos_ects
     * @param horas
     */
    public Modulo(String nombre, String curso, int creditos_ects, int horas) {
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
        //this.codigo = this.obtenerCodigoSQL();
        //this.codigo_ciclo = ciclo.obtenerCodigoSQL();
    }
    
    public int obtenerCodigoSQL(){
     int codigoSQL = 0;
     return codigoSQL; //Aqui habra que devolver el codigo del modulo de la base de datos
    }
    
    
         
}
