/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import servicios.GestionBaseDeDatos;
/**
 *
 * @author isard
 */
public class LineaMatricula { // En esta clase no hace falta implementar la interfaz persistenciaSQL
    // Aqui va la creacion del objeto LineaMatricula, el cual deberemos meter en la base de datos 
    private int codigo_matricula;
    private Matricula matricula;
    private int codigo_modulo;
    private Modulo modulo;
    private int repeticion;
    private double calificacion_primera;
    private double calificacion_segunda;

    /**
     * Construye nuevas LineasMatricula desde el programa
     * @param repeticion
     * @param calificacion_primera
     * @param calificacion_segunda 
     */
    public LineaMatricula(int repeticion, double calificacion_primera, double calificacion_segunda) {
        this.repeticion = repeticion;
        this.calificacion_primera = calificacion_primera;
        this.calificacion_segunda = calificacion_segunda;
        this.codigo_modulo = GestionBaseDeDatos.leerCodigoBDD("modulo");
        this.codigo_matricula = GestionBaseDeDatos.leerCodigoBDD("matricula");
    }

    /**
     * Construye desde la BDD la lineaMatricula
     * @param codigo_matricula
     * @param matricula
     * @param codigo_modulo
     * @param modulo
     * @param repeticion
     * @param calificacion_primera
     * @param calificacion_segunda 
     */
    public LineaMatricula(int codigo_matricula, Matricula matricula, int codigo_modulo, Modulo modulo, int repeticion, double calificacion_primera, double calificacion_segunda) {
        this.codigo_matricula = codigo_matricula;
        this.matricula = matricula;
        this.codigo_modulo = codigo_modulo;
        this.modulo = modulo;
        this.repeticion = repeticion;
        this.calificacion_primera = calificacion_primera;
        this.calificacion_segunda = calificacion_segunda;
    }

    
    
    
    
    
}
