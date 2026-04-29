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
    private Matricula matricula;
    private int codigo_modulo;
    private Modulo modulo;
    private int año_academico;
    private String estado;
    private double importe;

    /**
     *
     * @param año_academico
     * @param estado
     * @param importe
     */
    public LineaMatricula(int año_academico, String estado, double importe) {
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
        //this.codigo_modulo = modulo.obtenerCodigoSQL();
        //this.codigo_matricula = matricula.obtenerCodigoSQL();
    }
    
    
    
    
}
