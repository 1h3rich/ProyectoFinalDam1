/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
/**
 *
 * @author isard
 */
public class LineaMatricula { // En esta clase no hace falta implementar la interfaz persistenciaSQL
    // Aqui va la creacion del objeto LineaMatricula, el cual deberemos meter en la base de datos 
    private int codigo_matricula;
    private int codigo_modulo;
    private int repeticion;
    private double calificacion_primera;
    private double calificacion_segunda;

    /**
     * Construye nuevas LineasMatricula desde el programa
     * @param ma
     * @param mo
     * @param repeticion
     * @param calificacion_primera
     * @param calificacion_segunda 
     */
    public LineaMatricula(Matricula ma, Modulo mo, int repeticion, double calificacion_primera, double calificacion_segunda) {
        this.repeticion = repeticion;
        this.calificacion_primera = calificacion_primera;
        this.calificacion_segunda = calificacion_segunda;
        this.codigo_modulo = ma.getCodigo();
        this.codigo_matricula = mo.getCodigo();
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
        this.codigo_modulo = codigo_modulo;
        this.repeticion = repeticion;
        this.calificacion_primera = calificacion_primera;
        this.calificacion_segunda = calificacion_segunda;
    }

    public int getCodigo_matricula() {
        return codigo_matricula;
    }

    public int getCodigo_modulo() {
        return codigo_modulo;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public double getCalificacion_primera() {
        return calificacion_primera;
    }

    public double getCalificacion_segunda() {
        return calificacion_segunda;
    }

    
    
    
    
    
}
