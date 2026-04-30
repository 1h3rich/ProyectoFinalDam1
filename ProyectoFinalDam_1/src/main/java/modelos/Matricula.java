/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author isard
 */
public class Matricula {
    // Aqui va la creacion del objeto Matricula, el cual deberemos meter en la base de datos 
    private int codigo;
    private int codigo_alumno ;
    private Alumno alumno;
    private int año_academico;
    private String estado;
    private double importe;

    /**
     * AÑADIR MATRICULA MANUAL
     * @param año_academico
     * @param estado
     * @param importe
     */
    public Matricula(int año_academico, String estado, double importe) {
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
        //this.codigo = this.obtenerCodigoSQL();
        //this.codigo_alumno = alumno.obtenerCodigoSQL();
    }

    /**
     * Constructor automatico para los datos obtenidos desde la base de datos
     * @param codigo
     * @param codigo_alumno
     * @param año_academico
     * @param estado
     * @param importe 
     */
    public Matricula(int codigo, int codigo_alumno, int año_academico, String estado, double importe) {
        this.codigo = codigo;
        this.codigo_alumno = codigo_alumno;
        this.año_academico = año_academico;
        this.estado = estado;
        this.importe = importe;
    }
    
    //Getters

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_alumno() {
        return codigo_alumno;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public int getAño_academico() {
        return año_academico;
    }

    public String getEstado() {
        return estado;
    }

    public double getImporte() {
        return importe;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    
    
    
}
