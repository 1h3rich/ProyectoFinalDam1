/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.time.LocalDate;

/**
 *
 * @author isard
 */
public class Matricula {
    // Aqui va la creacion del objeto Matricula, el cual deberemos meter en la base de datos 
    private int codigo;
    private int codigo_alumno ;
    private Alumno alumno;
    private LocalDate año_academico;
    private String estado;
    private double importe;

    /**
     *
     * @param año_academico
     * @param estado
     * @param importe
     */
    public Matricula(LocalDate año_academico, String estado, double importe) {
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
        //this.codigo = this.obtenerCodigoSQL();
        //this.codigo_alumno = alumno.obtenerCodigoSQL();
    }
    
    
    public int obtenerCodigoSQL(){
     return 0 ; //Aqui habra que devolver el codigo del Matricula de la base de datos
    }
    
}
