/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import servicios.BaseDeDatos.ConsultasEspecificas;
import servicios.BaseDeDatos.Multitable;
/**
 *
 * @author isard
 */
public class Modulo{
    // Aqui va la creacion del objeto Modulo, el cual deberemos meter en la base de datos 
    private int codigo;
    private int codigo_ciclo;
    private String nombre;
    private String curso;
    private int creditos_ects;
    private int horas;

    /**
     * Creacion manual de nuevos modulos desde el programa
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
        this.codigo = ConsultasEspecificas.leerCodigoBDD("modulo");
        this.codigo_ciclo = ConsultasEspecificas.leerCodigoBDD("ciclo");
    }    

    /**
     * Creacion automatica desde la base de datos
     * @param codigo
     * @param codigo_ciclo
     * @param nombre
     * @param curso
     * @param creditos_ects
     * @param horas 
     */
    public Modulo(int codigo, int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {
        this.codigo = codigo;
        this.codigo_ciclo = codigo_ciclo;
        this.nombre = nombre;
        this.curso = curso;
        this.creditos_ects = creditos_ects;
        this.horas = horas;
    }
    
    
    // Getters

    public int getCodigo() {
        return codigo;
    }
    
    
         
}
